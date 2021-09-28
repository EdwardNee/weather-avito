package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class Lst @Inject constructor(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feels_like: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<Weather>
) : java.io.Serializable