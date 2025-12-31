package org.souhub.aihomedesign.client.internal.models

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.souhub.aihomedesign.client.models.ItemRemovalOrderId
import org.souhub.aihomedesign.client.models.ItemRemovalMaskOrderId
import org.souhub.aihomedesign.client.models.ServiceName

@Serializable
internal data class ItemRemovalOrderRequest(
    @SerialName("order_id")
    val orderId: ItemRemovalOrderId
)

@Serializable
internal data class ItemRemovalMaskOrderRequest(
    @SerialName("order_id")
    val orderId: ItemRemovalMaskOrderId
) {
    @OptIn(ExperimentalSerializationApi::class)
    @EncodeDefault((EncodeDefault.Mode.ALWAYS))
    @SerialName("service_name")
    val serviceName: String = ServiceName.ItemRemovalMask.value
}