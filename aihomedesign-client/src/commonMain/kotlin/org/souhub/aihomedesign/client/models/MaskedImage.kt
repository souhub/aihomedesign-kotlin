package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MaskedImage(
    @SerialName("image_id")
    public val imageId: ImageId,

    @SerialName("url")
    public val url: String
)