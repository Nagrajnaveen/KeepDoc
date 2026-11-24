package com.nags.keepdoc.data

import kotlinx.coroutines.delay

class SyncManager(private val repository: IDocumentRepository) {
    /**
     * Simulates Google Drive cloud syncing for documents with PENDING_SYNC status.
     */
    suspend fun syncPendingDocuments(): Boolean {
        delay(100)
        return true
    }
}
