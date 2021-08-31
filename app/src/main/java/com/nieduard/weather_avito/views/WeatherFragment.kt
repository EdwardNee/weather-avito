package com.nieduard.weather_avito.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.viewmodels.WeatherViewModel
import com.nieduard.weather_avito.modelfactories.WeatherModelFactory
import com.nieduard.weather_avito.viewmodels.IShowToast
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

interface IOpenNewCityFragment {
    fun onOpenNewCityFragment()
}

class WeatherFragment : Fragment() {

    private lateinit var temp: TextView
    private lateinit var windV: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var sunrise: TextView
    private lateinit var location: TextView
    private lateinit var time: TextView
    private lateinit var searchCity: TextInputLayout

    private val viewModel: WeatherViewModel by viewModels { WeatherModelFactory(showToastListener!!) }

    private var newCityListener: IOpenNewCityFragment? = null
    private var showToastListener: IShowToast? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IOpenNewCityFragment)
            newCityListener = context

        if (context is IShowToast)
            showToastListener = context

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
        loadWeather("London")
    }

    override fun onDetach() {
        super.onDetach()
        newCityListener = null
    }

    private fun loadWeather(city: String) {
        try {
            val data = viewModel.loadData(city)
            viewModel.weatherData.observe(viewLifecycleOwner, { d ->
                bindUI(d, city)
            })

            Log.d("LONDONTAG", data.toString())
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                context,
                e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Binds UI with data, loaded from openweathermap api.
     */
    private fun bindUI(data: WeatherModel, city: String) {
        val currentDay = data.list[0]
        temp.text = context?.getString(R.string.w_degree, (currentDay.temp.day.minus(273)).toInt())
        windV.text = context?.getString(R.string.w_wind_v, currentDay.speed.toInt())
        humidity.text =
            context?.getString(R.string.w_humidity, currentDay.humidity.toString() + "%")
        location.text = context?.getString(R.string.w_location, city)
        sunrise.text = dateFromMillis(currentDay.sunrise.toString())
        pressure.text = context?.getString(R.string.w_pressure, currentDay.pressure)
//        time.text = getLocalDate()

        location.setOnClickListener {
//            newCityListener?.onOpenNewCityFragment()
            searchCity.visibility = View.VISIBLE
            location.visibility = View.GONE
        }

        searchCity.setEndIconOnClickListener {
            location.visibility = View.VISIBLE
            searchCity.visibility = View.GONE
            loadWeather(searchCity.editText?.text.toString())

        }
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

    private fun getLocalDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dated = formatter.parse(Date().date.toString())
        return SimpleDateFormat("dd, mmm / HH:mm a").format(dated)
    }

    /**
     * Initializes the components.
     */
    private fun initViews(view: View) {
        temp = view.findViewById(R.id.w_degrees)
        windV = view.findViewById(R.id.w_1_info)
        humidity = view.findViewById(R.id.w_2_info)
        pressure = view.findViewById(R.id.w3_info)
        sunrise = view.findViewById(R.id.w4_info)
        location = view.findViewById(R.id.w_location)
        time = view.findViewById(R.id.w_time)
        searchCity = view.findViewById(R.id.et_search_loc)
    }
}