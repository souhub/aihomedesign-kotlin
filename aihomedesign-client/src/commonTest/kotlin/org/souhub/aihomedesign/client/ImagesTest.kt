package org.souhub.aihomedesign.client

import org.souhub.aihomedesign.client.models.ImageUploadRequest
import org.souhub.aihomedesign.client.models.ItemRemovalMaskUpload
import org.souhub.aihomedesign.client.models.ItemRemovalUpload
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ImagesTest : AIHomeDesignTest() {

    @Test
    fun testUploadItemRemovalImage() = test {
        val mockJson = """
            {
                "service_name": "service-item-removal",
                "order_id": "test-order-123"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }
        val request = ImageUploadRequest(
            image = testImageData,
            contentType = ContentType.Image.JPEG
        )

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.images.uploadItemRemovalImage(request)

        assertIs<ItemRemovalUpload>(result)
        assertEquals("test-order-123", result.orderId.value)
    }

    @Test
    fun testUploadItemRemovalMaskImage() = test {
        val mockJson = """
            {
                "service_name": "service-item-removal-mask",
                "order_id": "test-order-456"
            }
        """.trimIndent()

        val testImageData = ByteArray(10) { it.toByte() }
        val request = ImageUploadRequest(
            image = testImageData,
            contentType = ContentType.Image.JPEG
        )

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.images.uploadItemRemovalMaskImage(request)

        assertIs<ItemRemovalMaskUpload>(result)
        assertEquals("test-order-456", result.orderId.value)
    }
}