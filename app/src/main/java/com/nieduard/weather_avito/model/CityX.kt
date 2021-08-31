package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable

@Serializable
data class CityX(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)