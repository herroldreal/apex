package com.apex.remotesource.api

import com.apex.remotesource.dto.CharacterDto
import com.apex.remotesource.dto.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @Headers("Cache-Control: max-age=86400")
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int? = null): Response<ResultResponse<List<CharacterDto>>>

    @GET("character/{id}")
    suspend fun characterDetail(@Path("id") id: Int): Response<CharacterDto>
}
