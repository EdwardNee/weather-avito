package com.nieduard.weather_avito.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nieduard.weather_avito.service.WeatherAPI
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.viewmodels.WeatherViewModel

class WeatherModelFactory(private val listener: IShowToast?, private val retrofitModule: WeatherAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        WeatherViewModel::class.java -> WeatherViewModel(listener, retrofitModule)
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}