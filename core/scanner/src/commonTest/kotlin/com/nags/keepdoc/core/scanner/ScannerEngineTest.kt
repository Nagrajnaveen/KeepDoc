package com.nags.keepdoc.core.scanner

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertFailsWith

class ScannerEngineTest {

    private val scannerEngine = ScannerEngine()

    @Test
    fun testProcessScanSuccess() {
        val dummyData = byteArrayOf(1, 2, 3, 4, 5)
        val result = scannerEngine.processScan(dummyData, 100, 200)

        assertNotNull(result)
        assertEquals(100, result.width)
        assertEquals(200, result.height)
        assertEquals(4, result.detectedCorners.size)
        assertEquals(dummyData.contentHashCode(), result.croppedData.contentHashCode())
    }

    @Test
    fun testProcessScanInvalidInput() {
        assertFailsWith<IllegalArgumentException> {
            scannerEngine.processScan(byteArrayOf(), 100, 200)
        }
        assertFailsWith<IllegalArgumentException> {
            scannerEngine.processScan(byteArrayOf(1), 0, 200)
        }
    }
}
