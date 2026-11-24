package com.nags.keepdoc.data

import com.nags.keepdoc.domain.Document
import com.nags.keepdoc.domain.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface IDocumentRepository {
    fun getDocuments(): Flow<List<Document>>
    suspend fun saveDocument(document: Document)
    suspend fun deleteDocument(id: String)
    suspend fun updateSyncStatus(id: String, status: SyncStatus)
}

class InMemoryDocumentRepository : IDocumentRepository {
    private val documentsFlow = MutableStateFlow<List<Document>>(emptyList())

    override fun getDocuments(): Flow<List<Document>> = documentsFlow

    override suspend fun saveDocument(document: Document) {
        documentsFlow.update { current -> listOf(document) + current.filter { it.id != document.id } }
    }

    override suspend fun deleteDocument(id: String) {
        documentsFlow.update { current -> current.filter { it.id != id } }
    }

    override suspend fun updateSyncStatus(id: String, status: SyncStatus) {
        documentsFlow.update { current ->
            current.map { if (it.id == id) it.copy(syncStatus = status) else it }
        }
    }
}
