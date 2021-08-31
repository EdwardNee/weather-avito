package com.nieduard.weather_avito.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.service.RetrofitModule
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class WeatherViewModel(private val listener: IShowToast) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherModel>()
    val weatherData: LiveData<WeatherModel> get() = _weatherData

    val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d(
            "this_tag_exc",
            "ex handled: ${throwable.message}"
        )
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + handlerException)

    fun loadData(cityname: String) {
//        coroutineScope.launch {
        Log.d("loadData", "loadData: HERE")
        val rm = RetrofitModule().weatherApi
        rm.getData(cityname, RetrofitModule.API_KEY).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                _weatherData.value = response.body()
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("onFailure_loast", "fa")
                listener.onShowToast()
            }
        })
//        }
    }
}


interface IShowToast {
    fun onShowToast()
}

