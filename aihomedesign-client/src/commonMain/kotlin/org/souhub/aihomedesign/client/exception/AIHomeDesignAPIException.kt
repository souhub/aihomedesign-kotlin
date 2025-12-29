package org.souhub.aihomedesign.client.exception

/**
 * Represents an exception thrown when an error occurs while interacting with the AI Home Design API.
 *
 * @property statusCode the HTTP status code associated with the error.
 * @property error an instance of [org.souhub.aihomedesign.client.exception.AIHomeDesignError] containing information about the error that occurred.
 */
public sealed class AIHomeDesignAPIException(
    public val statusCode: Int,
    public val error: AIHomeDesignError,
    throwable: Throwable? = null,
) : AIHomeDesignException(message = error.error, throwable = throwable)

/**
 * Represents an exception thrown when an invalid request is made to the AI Home Design API.
 */
public class InvalidRequestException(
    statusCode: Int,
    error: AIHomeDesignError,
    throwable: Throwable? = null
) : AIHomeDesignAPIException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an authentication error occurs while interacting with the AI Home Design API.
 */
public class AuthenticationException(
    statusCode: Int,
    error: AIHomeDesignError,
    throwable: Throwable? = null
) : AIHomeDesignAPIException(statusCode, error, throwable)

/**
 * Represents an exception thrown when a permission error occurs while interacting with the AI Home Design API.
 */
public class PermissionException(
    statusCode: Int,
    error: AIHomeDesignError,
    throwable: Throwable? = null
) : AIHomeDesignAPIException(statusCode, error, throwable)

/**
 * Represents an exception thrown when the AI Home Design API rate limit is exceeded.
 */
public class RateLimitException(
    statusCode: Int,
    error: AIHomeDesignError,
    throwable: Throwable? = null
) : AIHomeDesignAPIException(statusCode, error)

/**
 * Represents an exception thrown when an unknown error occurs while interacting with the AI Home Design API.
 * This exception is used when the specific type of error is not covered by the existing subclasses.
 */
public class UnknownAPIException(
    statusCode: Int,
    error: AIHomeDesignError,
    throwable: Throwable? = null
) : AIHomeDesignAPIException(statusCode, error, throwable)