package org.souhub.aihomedesign.client.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ImageUploadResponse(
    @SerialName("order_id")
    val orderId: String
)