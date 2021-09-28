package com.nieduard.weather_avito.model

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class Weather @Inject constructor(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)