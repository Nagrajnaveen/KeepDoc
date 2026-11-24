package com.nags.keepdoc.feature.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nags.keepdoc.data.IDocumentRepository
import com.nags.keepdoc.data.SyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SyncViewModel(
    private val syncManager: SyncManager,
    private val documentRepository: IDocumentRepository
) : ViewModel() {

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    fun triggerGoogleDriveSync() {
        viewModelScope.launch {
            _isSyncing.update { true }
            syncManager.syncPendingDocuments()
            _isSyncing.update { false }
        }
    }
}
