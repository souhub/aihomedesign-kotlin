package org.souhub.aihomedesign.client.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("service_name")
public sealed interface ImageUpload {
    @SerialName("order_id")
    public val orderId: OrderId
}

@Serializable
@SerialName("service-item-removal")
public data class ItemRemovalUpload(
    @SerialName("order_id")
    override val orderId: OrderId
) : ImageUpload

@Serializable
@SerialName("service-item-removal-mask")
public data class ItemRemovalMaskUpload(
    @SerialName("order_id")
    override val orderId: OrderId
) : ImageUpload