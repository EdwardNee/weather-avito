package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class WeatherModel @Inject constructor(
    val city: CityX,
    val cnt: Int,
    val cod: String,
    val list: ArrayList<Lst>,
    val message: Double
)