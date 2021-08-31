package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherModel(
    val city: CityX,
    val cnt: Int,
    val cod: String,
    val list: List<Lst>,
    val message: Double
)