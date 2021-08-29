package com.nieduard.weather_avito

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nieduard.weather_avito.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var temp: TextView
    private lateinit var wind_v: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var sunrise: TextView
    private lateinit var location: TextView
    private lateinit var time: TextView

    private val viewModel: WeatherViewModel by viewModels { WeatherModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        initViews(view)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = viewModel.loadData("Moscow")

        viewModel.weatherData.observe(viewLifecycleOwner, { d ->
            d?.let { bindUI(d) }
        })
        Log.d("TAG", data.toString())
    }

    /**
     * Binds UI with data, loaded from openweathermap api.
     */
    private fun bindUI(data: WeatherModel) {
        temp.text = context?.getString(R.string.w_degree, (data.main.temp - 273).toInt())
        wind_v.text = context?.getString(R.string.w_wind_v, data.wind.speed.toInt())
        humidity.text = context?.getString(R.string.w_humidity, data.main.humidity.toString() + "%")
        location.text = context?.getString(R.string.w_location, data.name)
        sunrise.text = dateFromMillis(data.sys.sunrise.toString())
        pressure.text = context?.getString(R.string.w_pressure, data.main.pressure)
    }

    /**
     * Converting millis to date.
     */
    private fun dateFromMillis(s: String): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm a", Locale.getDefault())
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun getLocalDate(date: String): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dated = formatter.parse(date)
        return SimpleDateFormat("dd, mmm / HH:mm a").format(dated)
    }

    /**
     * Initializes the components.
     */
    private fun initViews(view: View) {
        temp = view.findViewById(R.id.w_degrees)
        wind_v = view.findViewById(R.id.w_1_info)
        humidity = view.findViewById(R.id.w_2_info)
        pressure = view.findViewById(R.id.w3_info)
        sunrise = view.findViewById(R.id.w4_info)
        location = view.findViewById(R.id.w_location)
        time = view.findViewById(R.id.w_time)
    }
}