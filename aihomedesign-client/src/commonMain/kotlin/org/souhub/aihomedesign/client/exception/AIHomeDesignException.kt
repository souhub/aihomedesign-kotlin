package org.souhub.aihomedesign.client.exception

/** AI Home Design client exception */
public sealed class AIHomeDesignException(
    message: String? = null,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)

/** Runtime Http Client exception */
public class AIHomeDesignHttpException(
    throwable: Throwable? = null,
) : AIHomeDesignException(throwable?.message, throwable)

/** An exception thrown in case of a server error */
public class AIHomeDesignServerException(
    throwable: Throwable? = null,
) : AIHomeDesignException(message = throwable?.message, throwable = throwable)