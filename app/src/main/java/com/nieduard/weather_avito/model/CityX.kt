package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class CityX @Inject constructor(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)