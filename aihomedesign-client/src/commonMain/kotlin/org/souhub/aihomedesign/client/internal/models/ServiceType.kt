package org.souhub.aihomedesign.client.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ServiceType(public val value: String) {
    @SerialName("service-creative-designer")
    CreativeDesigner("service-creative-designer"),

    @SerialName("service-ai-virtual-staging")
    AiVirtualStaging("service-ai-virtual-staging"),

    @SerialName("service-item-removal")
    ItemRemoval("service-item-removal"),

    @SerialName("service-item-removal-mask")
    ItemRemovalMask("service-item-removal-mask"),

    @SerialName("service-day-to-dusk")
    DayToDusk("service-day-to-dusk"),

    @SerialName("service-image-enhancement")
    ImageEnhancement("service-image-enhancement")
}