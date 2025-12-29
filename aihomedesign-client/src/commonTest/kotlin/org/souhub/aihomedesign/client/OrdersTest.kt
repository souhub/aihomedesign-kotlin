package org.souhub.aihomedesign.client

import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.models.ItemRemovalMaskUpload
import org.souhub.aihomedesign.client.models.ItemRemovalUpload
import org.souhub.aihomedesign.client.models.OrderId
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals

class OrdersTest : AIHomeDesignTest() {

    @Test
    fun testSubmitItemRemovalOrder() = test {
        val mockJson = """
            {
                "order_id": "test-order-123",
                "image_id": "test-image-456",
                "eta": 30
            }
        """.trimIndent()

        val upload = ItemRemovalUpload(OrderId("test-order-123"))

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.orders.submitItemRemovalOrder(upload)

        assertEquals("test-order-123", result.orderId.value)
        assertEquals("test-image-456", result.imageId.value)
        assertEquals(30, result.eta)
    }

    @Test
    fun testSubmitItemRemovalMaskOrder() = test {
        val mockJson = """
            {
                "order_id": "test-order-789",
                "image_id": "test-image-101",
                "eta": 45
            }
        """.trimIndent()

        val upload = ItemRemovalMaskUpload(OrderId("test-order-789"))

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.orders.submitItemRemovalMaskOrder(upload)

        assertEquals("test-order-789", result.orderId.value)
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

        val upload = ItemRemovalMaskUpload(OrderId("test-order-222"))
        val maskImage = ByteArray(10) { it.toByte() }

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.orders.applyManualMask(upload, maskImage)

        assertEquals("test-order-222", result.orderId.value)
        assertEquals("test-image-333", result.imageId.value)
        assertEquals(60, result.eta)
    }

    @Test
    fun testRetrieveLatestMaskedImage() = test {
        val mockJson = """
            {
                "image_id": "test-image-444",
                "url": "https://example.com/masked-image.png"
            }
        """.trimIndent()

        val upload = ItemRemovalMaskUpload(OrderId("test-order-555"))

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.orders.retrieveLatestMaskedImage(upload)

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

        val upload = ItemRemovalMaskUpload(OrderId("test-order-666"))
        val imageId = ImageId("test-image-777")

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.orders.finalizeOrder(upload, imageId)

        assertEquals("Order finalized successfully", result.message)
    }
}