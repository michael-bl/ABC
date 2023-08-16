package com.empresa.abc.controller

import com.empresa.abc.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRoutes {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Weather

}
