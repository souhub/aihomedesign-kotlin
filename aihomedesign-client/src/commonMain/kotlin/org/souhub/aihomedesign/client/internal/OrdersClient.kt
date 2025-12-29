package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.Orders
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.models.Order
import org.souhub.aihomedesign.client.internal.models.OrderRequest
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.internal.models.ItemRemovalMaskRequest
import org.souhub.aihomedesign.client.internal.models.ItemRemovalRequest
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
import org.souhub.aihomedesign.client.models.FinalizedOrder
import org.souhub.aihomedesign.client.models.ItemRemovalMaskUpload
import org.souhub.aihomedesign.client.models.ItemRemovalUpload
import org.souhub.aihomedesign.client.models.MaskedImage

internal class OrdersClient(
    private val requester: AIHomeDesignRequester
) : Orders {
    private suspend fun submitOrder(request: OrderRequest): Order {
        return requester.perform {
            it.post(ApiPath.ORDER) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    override suspend fun submitItemRemovalOrder(upload: ItemRemovalUpload): Order {
        return submitOrder(ItemRemovalRequest(orderId = upload.orderId))
    }

    override suspend fun submitItemRemovalMaskOrder(upload: ItemRemovalMaskUpload): Order {
        return submitOrder(ItemRemovalMaskRequest(orderId = upload.orderId))
    }

    override suspend fun applyManualMask(
        upload: ItemRemovalMaskUpload,
        maskImage: ByteArray
    ): Order {
        return requester.perform {
            it.submitFormWithBinaryData(
                url = ApiPath.MANUAL_MASK,
                formData = formData {
                    append(
                        key = "order_id",
                        value = upload.orderId.value
                    )

                    append(
                        "mask",
                        maskImage,
                        Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Image.PNG.contentType)
                            append(HttpHeaders.ContentDisposition, "filename=\"mask.png\"")
                        }
                    )
                }
            )
        }
    }

    override suspend fun retrieveLatestMaskedImage(upload: ItemRemovalMaskUpload): MaskedImage {
        return requester.perform {
            it.get("${ApiPath.ORDER}/${upload.orderId.value}/latest-temp-image")
        }
    }

    override suspend fun finalizeOrder(upload: ItemRemovalMaskUpload, imageId: ImageId): FinalizedOrder {
        return requester.perform {
            it.submitForm(
                url = ApiPath.FINALIZE_ORDER,
                formParameters = parameters {
                    append("order_id", upload.orderId.value)
                    append("image_id", imageId.value)
                }
            )
        }
    }
}