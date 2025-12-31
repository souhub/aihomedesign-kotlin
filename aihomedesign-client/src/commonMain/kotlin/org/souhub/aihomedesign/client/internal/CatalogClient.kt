package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.models.GetSpacesResponse
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.souhub.aihomedesign.client.Catalog
import org.souhub.aihomedesign.client.models.ServiceName

internal class CatalogClient(
    private val requester: AIHomeDesignRequester
): Catalog {
    override suspend fun getSpaces(
        active: Boolean?,
        serviceName: ServiceName?
    ): GetSpacesResponse {
        return requester.perform {
            it.get(ApiPath.SPACES) {
                parameter("active", active)
                parameter("service_name", serviceName?.value)
            }
        }
    }
}