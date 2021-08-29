package com.nieduard.weather_avito.service

import com.nieduard.weather_avito.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getData(@Query("q") cityname: String, @Query("appid") apiKey: String): WeatherModel
}