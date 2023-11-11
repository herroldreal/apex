package com.apex.domain

import com.apex.domain.models.InfoBO

data class PagingResult<T>(
    val info: InfoBO?,
    val results: List<T>? = emptyList()
)