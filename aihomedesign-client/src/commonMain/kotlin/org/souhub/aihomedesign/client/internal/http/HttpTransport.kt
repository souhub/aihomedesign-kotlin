package org.souhub.aihomedesign.client.internal.http

import org.souhub.aihomedesign.client.exception.AIHomeDesignAPIException
import org.souhub.aihomedesign.client.exception.AIHomeDesignError
import org.souhub.aihomedesign.client.exception.AIHomeDesignHttpException
import org.souhub.aihomedesign.client.exception.AIHomeDesignServerException
import org.souhub.aihomedesign.client.exception.AIHomeDesignTimeoutException
import org.souhub.aihomedesign.client.exception.AuthenticationException
import org.souhub.aihomedesign.client.exception.GenericIOException
import org.souhub.aihomedesign.client.exception.InvalidRequestException
import org.souhub.aihomedesign.client.exception.PermissionException
import org.souhub.aihomedesign.client.exception.RateLimitException
import org.souhub.aihomedesign.client.exception.UnknownAPIException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.CancellationException
import kotlinx.io.IOException

internal class HttpTransport(
    private val httpClient: HttpClient,
) : AIHomeDesignRequester {

    override suspend fun <T : Any> perform(info: TypeInfo, block: suspend (HttpClient) -> HttpResponse): T {
        try {
            val response = block(httpClient)
            return response.body(info)
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    override fun close() {
        httpClient.close()
    }

    /**
     * Handles various exceptions that can occur during an API request and converts them into appropriate
     * [org.souhub.aihomedesign.client.exception.AIHomeDesignException] instances.
     */
    private suspend fun handleException(e: Throwable) = when (e) {
        is CancellationException -> e
        is ClientRequestException -> aiHomeDesignApiException(e)
        is ServerResponseException -> AIHomeDesignServerException(e)
        is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> AIHomeDesignTimeoutException(
            e
        )

        is IOException -> GenericIOException(e)
        else -> AIHomeDesignHttpException(e)
    }

    private suspend fun aiHomeDesignApiException(exception: ClientRequestException): AIHomeDesignAPIException {
        val response = exception.response
        val status = response.status.value
        val error = response.body<AIHomeDesignError>()

        return when (status) {
            429 -> RateLimitException(status, error, exception)
            400, 404, 409, 415 -> InvalidRequestException(status, error, exception)
            401 -> AuthenticationException(status, error, exception)
            403 -> PermissionException(status, error, exception)
            else -> UnknownAPIException(status, error, exception)
        }
    }
}

