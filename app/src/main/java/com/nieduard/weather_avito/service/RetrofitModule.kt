//package com.nieduard.weather_avito.service
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.create
//
///**
// * Module class, that helps to deal with weather API through [Retrofit].
// */
//class RetrofitModule {
//    companion object {
//        //Must not be in public access. Added to check the task.
//        const val API_KEY = "0971c9f9ed8206a186292d41b88e5961"
//        const val BASE_URL =
//            "https://api.openweathermap.org/data/2.5/"
//    }
//
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val weatherApi: WeatherAPI = retrofit.create()
//}