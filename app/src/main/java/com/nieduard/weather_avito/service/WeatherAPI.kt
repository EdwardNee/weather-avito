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
     * GET method to get weather data in [cityname].
     * forecast/daily?q={CITY_NAME}&appid={API_KEY}
     */
    @GET("forecast/daily")
    fun getData(
        @Query("q") cityname: String,
        @Query("appid") apiKey: String
    ): Call<WeatherModel>
}