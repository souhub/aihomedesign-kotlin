package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.AIHomeDesignConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json
import org.souhub.aihomedesign.client.internal.extensions.toKtorLogLevel
import org.souhub.aihomedesign.client.internal.extensions.toKtorLogger

internal fun createHttpClient(config: AIHomeDesignConfig): HttpClient {
    val client = config.engine?.let { engine ->
        HttpClient(engine)
    } ?: HttpClient()
    return client.config {
        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(DefaultJson))
        }

        install(Logging) {
            logger = config.logging.logger.toKtorLogger()
            level = config.logging.logLevel.toKtorLogLevel()
            if (config.logging.sanitize) {
                sanitizeHeader { header -> header in listOf("X-Api-Key") }
            }
        }

        install(HttpTimeout) {
            config.timeout?.let {
                socketTimeoutMillis = it.inWholeMilliseconds
                connectTimeoutMillis = it.inWholeMilliseconds
                requestTimeoutMillis = it.inWholeMilliseconds
            }
        }

        defaultRequest {
            url(config.baseUrl)
            header("X-Api-Key", config.apiKey)
        }

        expectSuccess = true
    }
}

internal val DefaultJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = false
}