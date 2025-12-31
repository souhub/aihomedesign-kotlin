package org.souhub.aihomedesign.client.internal

import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.parameters
import org.souhub.aihomedesign.client.ItemRemovalMask
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.internal.models.ImageUploadRequest
import org.souhub.aihomedesign.client.internal.models.ItemRemovalMaskOrderRequest
import org.souhub.aihomedesign.client.models.ApplyManualMaskResponse
import org.souhub.aihomedesign.client.models.ServiceName
import org.souhub.aihomedesign.client.models.FinalizeOrderResponse
import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.models.ItemRemovalMaskOrderId
import org.souhub.aihomedesign.client.models.MaskedImage
import org.souhub.aihomedesign.client.models.SubmitOrderResponse

internal class ItemRemovalMaskClient(
    private val imageUploader: ImageUploader,
    private val requester: AIHomeDesignRequester
) : ItemRemovalMask {
    override suspend fun uploadImage(
        imageBytes: ByteArray,
        mineType: String?
    ): ItemRemovalMaskOrderId {
        val request =
            mineType?.let { ImageUploadRequest(imageBytes, ContentType.parse(it)) } ?: ImageUploadRequest(imageBytes)
        val upload = imageUploader.uploadImage(request, ServiceName.ItemRemovalMask)
        return ItemRemovalMaskOrderId.from(upload.orderId)
    }

    override suspend fun submitOrder(orderId: ItemRemovalMaskOrderId): SubmitOrderResponse {
        return requester.perform {
            it.post(ApiPath.ORDER) {
                contentType(ContentType.Application.Json)
                setBody(ItemRemovalMaskOrderRequest(orderId = orderId))
            }
        }
    }

    override suspend fun applyManualMask(
        orderId: ItemRemovalMaskOrderId,
        maskImage: ByteArray
    ): ApplyManualMaskResponse {
        return requester.perform {
            it.submitFormWithBinaryData(
                url = ApiPath.MANUAL_MASK,
                formData = formData {
                    append(
                        key = "order_id",
                        value = orderId.value
                    )

                    append(
                        "mask",
                        maskImage,
                        Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Image.PNG.toString())
                            append(HttpHeaders.ContentDisposition, "filename=\"mask.png\"")
                        }
                    )
                }
            )
        }
    }

    override suspend fun getLastTempImage(orderId: ItemRemovalMaskOrderId): MaskedImage {
        return requester.perform {
            it.get("${ApiPath.ORDER}/${orderId.value}/last-temp-image")
        }
    }

    override suspend fun finalizeOrder(
        orderId: ItemRemovalMaskOrderId,
        imageId: ImageId
    ): FinalizeOrderResponse {
        return requester.perform {
            it.submitForm(
                url = ApiPath.FINALIZE_ORDER,
                formParameters = parameters {
                    append("order_id", orderId.value)
                    append("image_id", imageId.value)
                }
            )
        }
    }
}