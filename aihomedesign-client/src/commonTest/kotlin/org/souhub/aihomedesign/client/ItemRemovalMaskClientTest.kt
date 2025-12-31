package org.souhub.aihomedesign.client

import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.models.ItemRemovalMaskOrderId
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemRemovalMaskClientTest : AIHomeDesignTest() {

    @Test
    fun testUploadImage() = test {
        val mockJson = """
            {
                "order_id": "test-order-456"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.uploadImage(testImageData)

        assertEquals("test-order-456", result.value)
    }

    @Test
    fun testUploadImageWithMimeType() = test {
        val mockJson = """
            {
                "order_id": "test-order-457"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.uploadImage(testImageData, "image/jpeg")

        assertEquals("test-order-457", result.value)
    }

    @Test
    fun testSubmitOrder() = test {
        val mockJson = """
            {
                "order_id": "test-order-789",
                "image_id": "test-image-101",
                "eta": 45
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.submitOrder(ItemRemovalMaskOrderId.from("test-order-789"))

        assertEquals("test-order-789", result.orderId)
        assertEquals("test-image-101", result.imageId.value)
        assertEquals(45, result.eta)
    }

    @Test
    fun testApplyManualMask() = test {
        val mockJson = """
            {
                "order_id": "test-order-222",
                "image_id": "test-image-333",
                "eta": 60
            }
        """.trimIndent()

        val maskImage = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.applyManualMask(ItemRemovalMaskOrderId.from("test-order-222"), maskImage)

        assertEquals("test-order-222", result.orderId)
        assertEquals("test-image-333", result.imageId.value)
        assertEquals(60, result.eta)
    }

    @Test
    fun testGetLastTempImage() = test {
        val mockJson = """
            {
                "image_id": "test-image-444",
                "url": "https://example.com/masked-image.png"
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.getLastTempImage(ItemRemovalMaskOrderId.from("test-order-555"))

        assertEquals("test-image-444", result.imageId.value)
        assertEquals("https://example.com/masked-image.png", result.url)
    }

    @Test
    fun testFinalizeOrder() = test {
        val mockJson = """
            {
                "message": "Order finalized successfully"
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.itemRemovalMask.finalizeOrder(
            ItemRemovalMaskOrderId.from("test-order-666"),
            ImageId("test-image-777")
        )

        assertEquals("Order finalized successfully", result.message)
    }

    @Test
    fun testCompleteWorkflow() = test {
        val uploadMockJson = """
            {
                "order_id": "workflow-order-456"
            }
        """.trimIndent()

        val submitMockJson = """
            {
                "order_id": "workflow-order-456",
                "image_id": "workflow-image-789",
                "eta": 50
            }
        """.trimIndent()

        val maskedImageMockJson = """
            {
                "image_id": "masked-image-123",
                "url": "https://example.com/masked.png"
            }
        """.trimIndent()

        val finalizeMockJson = """
            {
                "message": "Order finalized successfully"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }

        var requestCount = 0
        val client = aiHomeDesign { _ ->
            requestCount++
            respond(
                content = when (requestCount) {
                    1 -> uploadMockJson
                    2 -> submitMockJson
                    3 -> maskedImageMockJson
                    else -> finalizeMockJson
                },
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }

        // 1. Upload image
        val orderId = client.itemRemovalMask.uploadImage(testImageData)
        assertEquals("workflow-order-456", orderId.value)

        // 2. Submit order
        val order = client.itemRemovalMask.submitOrder(orderId)
        assertEquals("workflow-order-456", order.orderId)

        // 3. Retrieve masked image
        val maskedImage = client.itemRemovalMask.getLastTempImage(orderId)
        assertEquals("masked-image-123", maskedImage.imageId.value)

        // 4. Finalize order
        val finalizedOrder = client.itemRemovalMask.finalizeOrder(orderId, maskedImage.imageId)
        assertEquals("Order finalized successfully", finalizedOrder.message)
    }
}