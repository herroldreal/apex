package com.apex.remotesource.dto

data class ResultResponse<T>(
    val info: InfoDto? = null,
    val results: T? = null,
)