package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class Temp @Inject constructor(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)