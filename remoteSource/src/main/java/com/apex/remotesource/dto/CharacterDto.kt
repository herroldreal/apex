package com.apex.remotesource.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "species") val species: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "origin") val originDto: OriginDto? = null,
    @Json(name = "location") val location: LocationDto? = null,
    @Json(name = "image") val image: String? = null,
    @Json(name = "episode") val episode: List<String>? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "created") val created: String? = null,
)
