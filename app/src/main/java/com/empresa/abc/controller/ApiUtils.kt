package com.empresa.abc.controller;

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: ApiRoutes = retrofit.create(ApiRoutes::class.java)
}
