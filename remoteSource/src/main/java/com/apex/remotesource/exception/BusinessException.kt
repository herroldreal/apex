package com.apex.remotesource.exception

/**
 * Exception thrown when some business rule was not followed
 * */
open class BusinessException(
    val errorType: ErrorType = ErrorType.UNEXPECTED,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)
