package com.nags.keepdoc.feature.sync

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SyncScreen(viewModel: SyncViewModel) {
    val isSyncing by viewModel.isSyncing.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Google Drive Backup & Sync", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(if (isSyncing) "Syncing with Google Drive..." else "Offline Mode Active (Optional Sync Ready)")
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.triggerGoogleDriveSync() },
            enabled = !isSyncing
        ) {
            Text(if (isSyncing) "Syncing..." else "Sync Now with Google Drive")
        }
    }
}
