package com.nags.keepdoc

import com.nags.keepdoc.data.InMemoryDocumentRepository
import com.nags.keepdoc.domain.Document
import com.nags.keepdoc.domain.SyncStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DocumentRepositoryTest {

    private val repository = InMemoryDocumentRepository()

    @Test
    fun testSaveAndRetrieveDocuments() = runBlocking {
        val doc = Document(
            id = "1",
            title = "Tax Receipt",
            filePath = "/path/to/doc.pdf",
            pageCount = 2,
            createdAt = 123456789L,
            syncStatus = SyncStatus.LOCAL_ONLY
        )

        repository.saveDocument(doc)
        val docs = repository.getDocuments().first()

        assertEquals(1, docs.size)
        assertEquals("Tax Receipt", docs[0].title)
        assertEquals(SyncStatus.LOCAL_ONLY, docs[0].syncStatus)
    }

    @Test
    fun testUpdateSyncStatus() = runBlocking {
        val doc = Document(
            id = "2",
            title = "Invoice",
            filePath = "/path/to/invoice.pdf",
            pageCount = 1,
            createdAt = 123456789L,
            syncStatus = SyncStatus.LOCAL_ONLY
        )

        repository.saveDocument(doc)
        repository.updateSyncStatus("2", SyncStatus.SYNCED)
        
        val docs = repository.getDocuments().first()
        assertEquals(SyncStatus.SYNCED, docs[0].syncStatus)
    }

    @Test
    fun testDeleteDocument() = runBlocking {
        val doc = Document(
            id = "3",
            title = "Note",
            filePath = "/path/to/note.pdf",
            pageCount = 1,
            createdAt = 123456789L
        )

        repository.saveDocument(doc)
        assertEquals(1, repository.getDocuments().first().size)

        repository.deleteDocument("3")
        assertTrue(repository.getDocuments().first().isEmpty())
    }
}
