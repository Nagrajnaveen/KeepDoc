package com.nags.keepdoc.feature.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nags.keepdoc.core.pdf.PdfGeneratorEngine
import com.nags.keepdoc.core.scanner.ScannerEngine
import com.nags.keepdoc.core.scanner.Point
import com.nags.keepdoc.data.IDocumentRepository
import com.nags.keepdoc.domain.Document
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ScannerStep {
    object Capture : ScannerStep()
    data class EditCrop(val corners: List<Point>) : ScannerStep()
}

data class ScannerUiState(
    val step: ScannerStep = ScannerStep.Capture,
    val isScanning: Boolean = false,
    val capturedPagesCount: Int = 0,
    val lastCapturedData: ByteArray? = null,
    val detectedCorners: List<Point> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ScannerUiState
        return step == other.step && isScanning == other.isScanning &&
                capturedPagesCount == other.capturedPagesCount &&
                detectedCorners == other.detectedCorners
    }
    override fun hashCode(): Int {
        var result = step.hashCode()
        result = 31 * result + isScanning.hashCode()
        result = 31 * result + capturedPagesCount
        result = 31 * result + detectedCorners.hashCode()
        return result
    }
}

class ScannerViewModel(
    private val scannerEngine: ScannerEngine = ScannerEngine(),
    private val pdfEngine: PdfGeneratorEngine = PdfGeneratorEngine(),
    private val documentRepository: IDocumentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    private val pages = mutableListOf<ByteArray>()

    fun captureSnap(rawImageData: ByteArray, width: Int, height: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true) }
            val scanResult = scannerEngine.processScan(rawImageData, width, height)
            pages.add(scanResult.croppedData)
            _uiState.update {
                it.copy(
                    isScanning = false,
                    step = ScannerStep.EditCrop(scanResult.detectedCorners),
                    lastCapturedData = scanResult.croppedData,
                    detectedCorners = scanResult.detectedCorners,
                    capturedPagesCount = pages.size
                )
            }
        }
    }

    fun updateCorners(newCorners: List<Point>) {
        _uiState.update { it.copy(detectedCorners = newCorners) }
    }

    fun saveDocument(title: String, onComplete: () -> Unit) {
        if (pages.isEmpty()) return
        viewModelScope.launch {
            val pdfBytes = pdfEngine.generatePdfBytes(pages)
            val filePath = "/documents/${title.lowercase().replace(" ", "_")}.pdf"
            val document = Document(
                id = kotlin.random.Random.nextInt().toString(),
                title = title,
                filePath = filePath,
                pageCount = pages.size,
                createdAt = 123456789L
            )
            documentRepository.saveDocument(document)
            pages.clear()
            _uiState.update { ScannerUiState() }
            onComplete()
        }
    }
}
