package org.souhub.aihomedesign.client

import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.souhub.aihomedesign.client.models.ItemRemovalOrderId
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemRemovalClientTest : AIHomeDesignTest() {

    @Test
    fun testUploadImage() = test {
        val mockJson = """
            {
                "order_id": "test-order-123"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemoval.uploadImage(testImageData)

        assertEquals("test-order-123", result.value)
    }

    @Test
    fun testUploadImageWithMimeType() = test {
        val mockJson = """
            {
                "order_id": "test-order-124"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemoval.uploadImage(testImageData, "image/x-sony-arw")

        assertEquals("test-order-124", result.value)
    }

    @Test
    fun testSubmitOrder() = test {
        val mockJson = """
            {
                "order_id": "test-order-123",
                "image_id": "test-image-456",
                "eta": 30
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemoval.submitOrder(ItemRemovalOrderId.from("test-order-123"))

        assertEquals("test-order-123", result.orderId)
        assertEquals("test-image-456", result.imageId.value)
        assertEquals(30, result.eta)
    }

    @Test
    fun testCompleteWorkflow() = test {
        val uploadMockJson = """
            {
                "order_id": "workflow-order-123"
            }
        """.trimIndent()

        val submitMockJson = """
            {
                "order_id": "workflow-order-123",
                "image_id": "workflow-image-456",
                "eta": 40
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        var requestCount = 0
        val client = aiHomeDesign { _ ->
            requestCount++
            respond(
                content = if (requestCount == 1) uploadMockJson else submitMockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }

        // 1. Upload image
        val orderId = client.itemRemoval.uploadImage(testImageData)
        assertEquals("workflow-order-123", orderId.value)

        // 2. Submit order
        val order = client.itemRemoval.submitOrder(orderId)
        assertEquals("workflow-order-123", order.orderId)
        assertEquals("workflow-image-456", order.imageId.value)
        assertEquals(40, order.eta)
    }
}