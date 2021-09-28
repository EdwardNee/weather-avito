package com.nieduard.weather_avito.model

import javax.inject.Inject

class Coords @Inject constructor(
    var lat: Double,
    var lon: Double
)