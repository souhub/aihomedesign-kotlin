package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FinalizeOrderResponse(
    @SerialName("message")
    public val message: String
)
