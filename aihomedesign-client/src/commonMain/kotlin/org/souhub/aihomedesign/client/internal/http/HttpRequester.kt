package org.souhub.aihomedesign.client.internal.http

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

/**
 * Http request performer.
 */
internal interface AIHomeDesignRequester : AutoCloseable {

    /**
     * Perform an HTTP request and get a result.
     */
    suspend fun <T : Any> perform(info: TypeInfo, block: suspend (HttpClient) -> HttpResponse): T
}

/**
 * Perform an HTTP request and get a result
 */
internal suspend inline fun <reified T> AIHomeDesignRequester.perform(noinline block: suspend (HttpClient) -> HttpResponse): T {
    return perform(typeInfo<T>(), block)
}