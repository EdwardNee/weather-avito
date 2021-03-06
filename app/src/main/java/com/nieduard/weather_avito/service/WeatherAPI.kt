package com.nieduard.weather_avito.service

import com.nieduard.weather_avito.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to get data from openweather api.
 */
interface WeatherAPI {

    /**
     * GET method to get weather data in [cityName].
     * forecast/daily?q={CITY_NAME}&appid={API_KEY}
     */
    @GET("forecast/daily")
    fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String
    ): Call<WeatherModel>


    @GET("forecast/daily")
    fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String
    ): Call<WeatherModel>
}