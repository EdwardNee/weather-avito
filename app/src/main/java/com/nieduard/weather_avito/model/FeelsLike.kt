package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class FeelsLike @Inject constructor(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)