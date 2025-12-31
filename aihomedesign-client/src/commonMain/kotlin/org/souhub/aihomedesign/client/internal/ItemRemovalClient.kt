package org.souhub.aihomedesign.client.internal

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.souhub.aihomedesign.client.ItemRemoval
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.internal.models.ImageUploadRequest
import org.souhub.aihomedesign.client.internal.models.ItemRemovalOrderRequest
import org.souhub.aihomedesign.client.models.ServiceName
import org.souhub.aihomedesign.client.models.ItemRemovalOrderId
import org.souhub.aihomedesign.client.models.SubmitOrderResponse

internal class ItemRemovalClient(
    private val imageUploader: ImageUploader,
    private val requester: AIHomeDesignRequester
) : ItemRemoval {
    override suspend fun uploadImage(
        imageBytes: ByteArray,
        mineType: String?
    ): ItemRemovalOrderId {
        val request =
            mineType?.let { ImageUploadRequest(imageBytes, ContentType.parse(it)) } ?: ImageUploadRequest(imageBytes)
        val upload = imageUploader.uploadImage(request, ServiceName.ItemRemoval)
        return ItemRemovalOrderId.from(upload.orderId)
    }

    override suspend fun submitOrder(orderId: ItemRemovalOrderId): SubmitOrderResponse {
        return requester.perform {
            it.post(ApiPath.ORDER) {
                contentType(ContentType.Application.Json)
                setBody(ItemRemovalOrderRequest(orderId = orderId))
            }
        }
    }
}