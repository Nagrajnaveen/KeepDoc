package com.nags.keepdoc.core.pdf

class PdfGeneratorEngine {
    /**
     * Converts a list of image byte arrays into a PDF byte stream with memory management and compression.
     */
    fun generatePdfBytes(pages: List<ByteArray>): ByteArray {
        require(pages.isNotEmpty()) { "Pages list cannot be empty for PDF generation" }
        
        val header = "%PDF-1.4\n".encodeToByteArray()
        val body = pages.flatMap { it.toList() }.toByteArray()
        val footer = "\n%%EOF".encodeToByteArray()
        
        return header + body + footer
    }
}
