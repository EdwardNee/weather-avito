package com.nieduard.weather_avito.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nieduard.weather_avito.viewmodels.IShowToast
import com.nieduard.weather_avito.viewmodels.WeatherViewModel
import java.lang.IllegalArgumentException

class WeatherModelFactory(private val listener: IShowToast) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        WeatherViewModel::class.java -> WeatherViewModel(listener)
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}