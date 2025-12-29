package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Order(
    @SerialName("order_id")
    public val orderId: OrderId,

    @SerialName("image_id")
    public val imageId: ImageId,

    @SerialName("eta")
    public val eta: Int
)