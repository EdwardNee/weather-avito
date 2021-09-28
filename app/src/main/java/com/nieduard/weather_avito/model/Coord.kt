package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class Coord @Inject constructor(
    val lat: Double,
    val lon: Double
)