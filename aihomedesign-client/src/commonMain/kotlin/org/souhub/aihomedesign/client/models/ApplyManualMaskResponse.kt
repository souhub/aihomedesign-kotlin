package org.souhub.aihomedesign.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the apply manual mask API, conforming to the API documentation interface.
 *
 * This represents the raw response structure as defined in the AI Home Design API.
 *
 * @property maskId The unique identifier for the applied mask.
 */
@Serializable
public data class ApplyManualMaskResponse(
    @SerialName("maskId")
    val maskId: MaskId,
)
