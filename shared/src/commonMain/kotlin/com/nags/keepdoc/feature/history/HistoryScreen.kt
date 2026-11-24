package com.nags.keepdoc.feature.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onScanNewClicked: () -> Unit
) {
    val documents by viewModel.documents.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("KeepDoc - Scanned Documents") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onScanNewClicked) {
                Text("+")
            }
        }
    ) { padding ->
        if (documents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📄", style = MaterialTheme.typography.displayMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No scanned documents yet.\nClick on + button to scan.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(documents, key = { it.id }) { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(doc.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Pages: ${doc.pageCount} | Status: ${doc.syncStatus}")
                        }
                    }
                }
            }
        }
    }
}
