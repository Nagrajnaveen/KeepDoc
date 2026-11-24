package com.nags.keepdoc.core.pdf

import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class PdfGeneratorEngineTest {

    private val pdfEngine = PdfGeneratorEngine()

    @Test
    fun testGeneratePdfSuccess() {
        val page1 = byteArrayOf(10, 20, 30)
        val page2 = byteArrayOf(40, 50, 60)
        val pdfBytes = pdfEngine.generatePdfBytes(listOf(page1, page2))

        assertNotNull(pdfBytes)
        assertTrue(pdfBytes.isNotEmpty())
        assertTrue(pdfBytes.decodeToString().startsWith("%PDF-1.4"))
        assertTrue(pdfBytes.decodeToString().endsWith("%%EOF"))
    }

    @Test
    fun testGeneratePdfEmptyPages() {
        assertFailsWith<IllegalArgumentException> {
            pdfEngine.generatePdfBytes(emptyList())
        }
    }
}
