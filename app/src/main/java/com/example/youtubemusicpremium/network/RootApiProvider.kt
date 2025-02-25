package com.example.youtubemusicpremium.network

object RootApiProvider {
    val retrofitService : RootApiService by lazy {
        retrofit.create(RootApiService::class.java)
    }
}