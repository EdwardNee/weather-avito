package com.nieduard.weather_avito.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.service.RetrofitModule
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.utils.TimeHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val listener: IShowToast) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherModel>()
    private val TAG: String = "DEBUG_TAG"
    val weatherData: LiveData<WeatherModel> get() = _weatherData

    val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d(
            TAG,
            "ex handled: ${throwable.message}"
        )
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + handlerException)

    /**
     * Tries to load weather data from openweathermap api in [cityname] city.
     */
    fun loadData(cityname: String) {
//        coroutineScope.launch {
        val rm = RetrofitModule().weatherApi

        rm.getData(cityname, RetrofitModule.API_KEY).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                if (response.code() == 404) {
                    listener.onShowToast(
                        "Possibly, the city name is incorrect. Please, try again",
                        Toast.LENGTH_LONG
                    )
                }
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                    TimeHelper.CITY_OFFSET = _weatherData.value?.city?.timezone
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                listener.onShowToast("Something went wrong.", Toast.LENGTH_LONG)
            }
        })
//        }
    }
}

