package com.nieduard.weather_avito.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitModule {
    companion object {
        const val API_KEY = "API_KEY"
        const val BASE_URL =
            "https://api.openweathermap.org/data/2.5/"
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApi: WeatherAPI = retrofit.create()
}