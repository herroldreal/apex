package com.apex.remotesource.extensions

import com.apex.remotesource.exception.BusinessException
import com.apex.remotesource.exception.ErrorType
import retrofit2.Response

fun <T> Response<T>.parseResponse(): T {
    val body = this.body()
    if (this.isSuccessful && body != null) {
        return body
    } else {
        throw Exception(this.message())
    }
}

fun <T> Response<T>.parseEmptyResponse() {
    if (!this.isSuccessful) {
        throw BusinessException(
            getErrorType(this.code()),
            "$this.code() $this.message()",
        )
    }
}

fun getErrorType(code: Int): ErrorType {
    return when (code) {
        401 -> ErrorType.UNAUTHORIZED
        409 -> ErrorType.CONFLICT
        else -> ErrorType.UNEXPECTED
    }
}