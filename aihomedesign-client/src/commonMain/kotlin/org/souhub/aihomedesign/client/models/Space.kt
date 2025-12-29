package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Space(
    @SerialName("id")
    public val id: String,

    @SerialName("name")
    public val name: String,

    @SerialName("title")
    public val title: String,

    @SerialName("description")
    public val description: String,

    @SerialName("prompt")
    public val prompt: String,

    @SerialName("service_ids")
    public val serviceIds: List<String>,

    @SerialName("icon_src")
    public val iconSrc: String,

    @SerialName("is_active")
    public val isActive: Boolean,

    @SerialName("priority")
    public val priority: Int,

    @SerialName("is_premium")
    public val isPremium: Boolean,

    @SerialName("is_multi_space")
    public val isMultiSpace: Boolean,
)