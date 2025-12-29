package org.souhub.aihomedesign.client.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error object returned by the AI Home Design API.
 */
@Serializable
public data class AIHomeDesignError(
    /**
     * A human-readable message providing more details about the error.
     * Example: "order not found"
     */
    @SerialName("error")
    val error: String? = null,

    /**
     * A machine-readable identifier for the specific error type.
     * Useful for programmatic error handling.
     * Example: "api_error_order_not_found"
     */
    @SerialName("key")
    val key: String? = null,
)