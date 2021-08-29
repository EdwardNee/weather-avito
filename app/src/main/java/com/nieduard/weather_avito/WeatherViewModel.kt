package com.nieduard.weather_avito

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.service.RetrofitModule
import com.nieduard.weather_avito.service.WeatherAPI
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherModel>()
    val weatherData: LiveData<WeatherModel> get() = _weatherData

    fun loadData(cityname: String) {
        viewModelScope.launch {
            val rm = RetrofitModule().weatherApi
            _weatherData.value = rm.getData(cityname, RetrofitModule.API_KEY)
        }
    }
}