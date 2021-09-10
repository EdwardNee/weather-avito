package com.nieduard.weather_avito.utils

import com.nieduard.weather_avito.model.Lst

interface IDaySelected {
    fun onDaySelected(day: Lst)
}