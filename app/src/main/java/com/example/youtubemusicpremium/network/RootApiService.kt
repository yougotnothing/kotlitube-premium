package com.example.youtubemusicpremium.network

import com.example.youtubemusicpremium.data.api.HomeResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.2.2:8000"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface RootApiService {
    @GET("/home")
    suspend fun getHome(): List<HomeResponse>
}

