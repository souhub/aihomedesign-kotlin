package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FinalizedOrder(
    @SerialName("message")
    public val message: String
)