package com.nags.keepdoc.feature.scanner

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.nags.keepdoc.core.scanner.CameraScannerPreview

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel,
    onDocumentSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var docTitle by remember { mutableStateOf("Scanned Document") }

    when (uiState.step) {
        is ScannerStep.Capture -> {
            CameraScannerPreview(
                onImageCaptured = { bytes, width, height ->
                    viewModel.captureSnap(bytes, width, height)
                }
            )
        }
        is ScannerStep.EditCrop -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Edit & Adjust Document Edges", style = MaterialTheme.typography.headlineSmall)
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 4f
                        drawRect(
                            color = Color.Green,
                            style = Stroke(width = strokeWidth)
                        )
                    }
                    Text("Auto-Cropped & Adjusted Preview", color = Color.White)
                }

                OutlinedTextField(
                    value = docTitle,
                    onValueChange = { docTitle = it },
                    label = { Text("Document Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveDocument(docTitle) { onDocumentSaved() } },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save PDF")
                    }
                }
            }
        }
    }
}
