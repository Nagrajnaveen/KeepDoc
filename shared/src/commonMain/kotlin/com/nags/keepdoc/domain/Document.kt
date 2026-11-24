package com.nags.keepdoc.domain

enum class SyncStatus {
    LOCAL_ONLY,
    PENDING_SYNC,
    SYNCED
}

data class Document(
    val id: String,
    val title: String,
    val filePath: String,
    val pageCount: Int,
    val createdAt: Long,
    val syncStatus: SyncStatus = SyncStatus.LOCAL_ONLY
)
