package org.souhub.aihomedesign.client

import org.souhub.aihomedesign.client.internal.DefaultAIHomeDesign
import org.souhub.aihomedesign.client.internal.createHttpClient
import org.souhub.aihomedesign.client.internal.http.HttpTransport
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.time.Duration.Companion.minutes

abstract class AIHomeDesignTest {
    internal fun aiHomeDesign(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): AIHomeDesign {
        val mockEngine = MockEngine(handler)
        val config = AIHomeDesignConfig(
            apiKey = "test-api",
            logging = LoggingConfig(logLevel = LogLevel.All),
            timeout = 1.minutes,
            engine = mockEngine,
        )
        val httpClient = createHttpClient(config)
        val transport = HttpTransport(httpClient)
        return DefaultAIHomeDesign(transport)
    }

    fun test(
        testBody: suspend TestScope.() -> Unit
    ) = runTest(timeout = 1.minutes, testBody = testBody)
}