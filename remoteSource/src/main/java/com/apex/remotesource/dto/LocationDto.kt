package com.apex.remotesource.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class LocationDto {
    @Json(name = "name")
    val name: String? = null

    @Json(name = "id")
    val location: String? = null
}