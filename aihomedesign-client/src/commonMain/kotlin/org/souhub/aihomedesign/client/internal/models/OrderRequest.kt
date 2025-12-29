package org.souhub.aihomedesign.client.internal.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import org.souhub.aihomedesign.client.models.OrderId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("service_name")
internal sealed interface OrderRequest {
    val orderId: OrderId
}

@Serializable
@SerialName("service-item-removal")
internal data class ItemRemovalRequest(
    @SerialName("order_id") override val orderId: OrderId
) : OrderRequest

@Serializable
@SerialName("service-item-removal-mask")
internal data class ItemRemovalMaskRequest(
    @SerialName("order_id")
    override val orderId: OrderId,
) : OrderRequest