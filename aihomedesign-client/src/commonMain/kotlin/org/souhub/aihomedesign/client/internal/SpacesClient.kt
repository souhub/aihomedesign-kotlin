package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.Spaces
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.internal.models.DataResponse
import org.souhub.aihomedesign.client.models.Space
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class SpacesClient(
    private val requester: AIHomeDesignRequester
): Spaces {
    override suspend fun getSpaces(
        active: Boolean?,
        serviceName: String?
    ): List<Space> {
        return requester.perform<DataResponse<List<Space>>> {
            it.get(ApiPath.SPACES) {
                parameter("active", active)
                parameter("service_name", serviceName)
            }
        }.data
    }
}