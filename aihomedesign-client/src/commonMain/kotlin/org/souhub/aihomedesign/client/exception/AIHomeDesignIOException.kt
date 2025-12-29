package org.souhub.aihomedesign.client.exception

/** An exception thrown in case of an I/O error */
public sealed class AIHomeDesignIOException(
    throwable: Throwable? = null,
) : AIHomeDesignException(message = throwable?.message, throwable = throwable)

/** An exception thrown in case a request times out. */
public class AIHomeDesignTimeoutException(
    throwable: Throwable
) : AIHomeDesignIOException(throwable = throwable)

/** An exception thrown in case of an I/O error */
public class GenericIOException(
    throwable: Throwable? = null,
) : AIHomeDesignIOException(throwable = throwable)