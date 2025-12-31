package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the submit order API, conforming to the API documentation interface.
 *
 * This represents the raw response structure as defined in the AI Home Design API.
 *
 * @property orderId The unique identifier for the order.
 * @property imageId The unique identifier for the image.
 * @property eta Estimated time of arrival in seconds.
 */
@Serializable
public data class SubmitOrderResponse(
    @SerialName("order_id")
    public val orderId: String,

    @SerialName("image_id")
    public val imageId: ImageId,

    @SerialName("eta")
    public val eta: Int
)

/**
 * User-friendly representation of the submit order result.
 *
 * This is a simplified version of [SubmitOrderResponse], excluding the order ID
 * for convenience when the order ID is already known from context.
 *
 * @property imageId The unique identifier for the image.
 * @property eta Estimated time of arrival in seconds.
 */
@Serializable
public data class SubmitOrderResult(
    @SerialName("image_id")
    public val imageId: ImageId,

    @SerialName("eta")
    public val eta: Int
)