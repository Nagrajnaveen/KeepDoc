package com.nags.keepdoc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nags.keepdoc.data.InMemoryDocumentRepository
import com.nags.keepdoc.data.SyncManager
import com.nags.keepdoc.feature.history.HistoryScreen
import com.nags.keepdoc.feature.history.HistoryViewModel
import com.nags.keepdoc.feature.scanner.ScannerScreen
import com.nags.keepdoc.feature.scanner.ScannerViewModel
import com.nags.keepdoc.feature.sync.SyncScreen
import com.nags.keepdoc.feature.sync.SyncViewModel

sealed class Screen(val title: String) {
    object History : Screen("History")
    object Scanner : Screen("Scan")
    object Sync : Screen("Google Drive Sync")
}

val sharedRepository = InMemoryDocumentRepository()
val sharedSyncManager = SyncManager(sharedRepository)

@Composable
fun App() {
    MaterialTheme {
        val historyViewModel = remember { HistoryViewModel(sharedRepository) }
        val scannerViewModel = remember { ScannerViewModel(documentRepository = sharedRepository) }
        val syncViewModel = remember { SyncViewModel(sharedSyncManager, sharedRepository) }

        var currentScreen by remember { mutableStateOf<Screen>(Screen.History) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == Screen.History,
                        onClick = { currentScreen = Screen.History },
                        label = { Text("History") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screen.Scanner,
                        onClick = { currentScreen = Screen.Scanner },
                        label = { Text("Scan") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screen.Sync,
                        onClick = { currentScreen = Screen.Sync },
                        label = { Text("Sync") },
                        icon = {}
                    )
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentScreen) {
                    is Screen.History -> HistoryScreen(
                        viewModel = historyViewModel,
                        onScanNewClicked = { currentScreen = Screen.Scanner }
                    )
                    is Screen.Scanner -> RequestCameraAndFilePermissions {
                        ScannerScreen(
                            viewModel = scannerViewModel,
                            onDocumentSaved = { currentScreen = Screen.History }
                        )
                    }
                    is Screen.Sync -> SyncScreen(viewModel = syncViewModel)
                }
            }
        }
    }
}
