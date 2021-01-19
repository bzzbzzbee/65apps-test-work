package com.example.apps65test.api

import com.example.apps65test.data.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitApi {
    @GET
    suspend fun loadResponse(@Url url: String): Response<Root>

}