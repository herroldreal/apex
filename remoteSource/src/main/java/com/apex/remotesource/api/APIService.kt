package com.apex.remotesource.api

import com.apex.remotesource.dto.CharacterDto
import com.apex.remotesource.dto.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int? = 1): Response<ResultResponse<List<CharacterDto>>>

    @GET("character/{id}")
    suspend fun characterDetail(@Path("id") id: Int): Response<CharacterDto>
}
