package org.souhub.aihomedesign.client

import io.ktor.client.engine.HttpClientEngine
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * AI Home Design client configuration.
 *
 * @param apiKey API key.
 * @param logging client logging configuration.
 * @param baseUrl Base URL for the AI Home Design API.
 * @param timeout HTTP client timeout.
 * @param engine Explicit Ktor engine for HTTP requests.
 */
public class AIHomeDesignConfig(
    public val apiKey: String,
    public val logging: LoggingConfig = LoggingConfig(),
    public val baseUrl: String = "https://api.aihomedesign.io/v1",
    public val timeout: Duration? = 30.seconds,
    public val engine: HttpClientEngine? = null,
)

/**
 * Defines the configuration parameters for logging.
 *
 * @property logLevel the level of logging to be used by the HTTP client.
 * @property logger the logger instance to be used by the HTTP client.
 * @property sanitize flag indicating whether to sanitize sensitive information (i.e., authorization header) in the logs
 */
public class LoggingConfig (
    public val logLevel: LogLevel = LogLevel.Headers,
    public val logger: Logger = Logger.Simple,
    public val sanitize: Boolean = true,
)

/**
 * Http client logging log level.
 */
public enum class LogLevel {
    All, Headers, Body, Info, None
}

/**
 * Http client logger.
 */
public enum class Logger {

    /**
     * Default logger to use.
     */
    Default,

    /**
     * Logger using println.
     */
    Simple,

    /**
     * Empty Logger for test purpose.
     */
    Empty
}