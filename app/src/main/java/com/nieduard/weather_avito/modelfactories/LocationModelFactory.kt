package com.nieduard.weather_avito.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nieduard.weather_avito.viewmodels.LocationViewModel
import java.lang.IllegalArgumentException

class LocationModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass){
        LocationViewModel::class.java -> LocationViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}
