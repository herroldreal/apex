package com.apex.remotesource.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class OriginDto {
    @Json(name = "name")
    val name: String? = null

    @Json(name = "url")
    val url: String? = null
}