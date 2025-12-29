package org.souhub.aihomedesign.client.internal.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DataResponse<T>(
    val data: T
)