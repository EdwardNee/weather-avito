package com.nieduard.weather_avito.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieduard.weather_avito.NetworkModule
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.service.WeatherAPI
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.utils.TimeHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WeatherViewModel(private val listener: IShowToast?, private val retrofitModule: WeatherAPI) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherModel>()
    private val tag: String = "DEBUG_TAG"
    private var locale: String = when (Locale.getDefault().displayLanguage.lowercase()) {
        "русский" -> "ru"
        else -> "en"
    }
    val weatherData: LiveData<WeatherModel> get() = _weatherData


    /**
     * Tries to load weather data from openweathermap api in [cityName] city.
     */
    fun loadData(cityName: String) {
//        val rm = RetrofitModule().weatherApi
        retrofitModule.getWeatherByCity(cityName, NetworkModule.API_KEY, locale)
            .enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if (response.code() == 404) {
                        listener?.onShowToast(
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
                    Log.d(tag, t.message.toString())
                    listener?.onShowToast("Something went wrong.", Toast.LENGTH_LONG)
                }
            })
    }

    fun loadData(lat: Double, lon: Double) {
        retrofitModule.getWeatherByCoordinates(lat, lon, NetworkModule.API_KEY, locale)
            .enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if (response.code() == 404) {
                        listener?.onShowToast(
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
                    Log.d(tag, t.message.toString())
                    listener?.onShowToast("Something went wrong.", Toast.LENGTH_LONG)
                }
            })
    }
}

