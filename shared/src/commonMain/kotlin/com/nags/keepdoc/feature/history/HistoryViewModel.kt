package com.nags.keepdoc.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nags.keepdoc.data.IDocumentRepository
import com.nags.keepdoc.domain.Document
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(private val documentRepository: IDocumentRepository) : ViewModel() {

    val documents: StateFlow<List<Document>> = documentRepository.getDocuments()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteDocument(id: String) {
        viewModelScope.launch {
            documentRepository.deleteDocument(id)
        }
    }
}
