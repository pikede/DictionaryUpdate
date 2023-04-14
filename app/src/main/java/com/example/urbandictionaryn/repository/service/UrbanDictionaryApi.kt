package com.example.urbandictionaryn.repository.service

import com.example.urbandictionaryn.models.JsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UrbanDictionaryApi {
    @Headers("x-rapidapi-key: 1eeb418dc6msh86aa4f3cc1c3055p1a437ajsnacfeee91a322")
    @GET("define")
    suspend fun getWordDefinitions(@Query(value = "term") string: String): Response<JsonResponse>
}