package com.nieduard.weather_avito.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieduard.weather_avito.model.Coords

class LocationViewModel : ViewModel() {
    private var _location = MutableLiveData<Coords?>()

    val location: MutableLiveData<Coords?> get() = _location

    fun initLocation(coords: Coords?) {
        _location.value = coords
    }

}