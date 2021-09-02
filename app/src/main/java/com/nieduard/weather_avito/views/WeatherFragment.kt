package com.nieduard.weather_avito.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.adapters.ForecastAdapter
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.modelfactories.WeatherModelFactory
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.utils.TimeHelper
import com.nieduard.weather_avito.viewmodels.WeatherViewModel

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

    private var showToastListener: IShowToast? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        view.findViewById<RecyclerView>(R.id.rv_forecast).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val adapter = ForecastAdapter()
            this.adapter = adapter
        }
    }

    override fun onDetach() {
        super.onDetach()
        showToastListener = null
    }

    private fun loadWeather(city: String) {
        viewModel.loadData(city)
        viewModel.weatherData.observe(viewLifecycleOwner, { d ->
            bindUI(d)
        })
    }

    /**
     * Binds UI with data, loaded from openweathermap api.
     */
    private fun bindUI(data: WeatherModel) {
        val currentDay = data.list[0]
        temp.text = context?.getString(R.string.w_degree, (currentDay.temp.day.minus(273)).toInt())
        windV.text = context?.getString(R.string.w_wind_v, currentDay.speed.toInt())
        humidity.text =
            context?.getString(R.string.w_humidity, currentDay.humidity.toString() + "%")
        location.text = data.city.name
        sunrise.text = TimeHelper.dateFromUnix(currentDay.sunrise, data.city.timezone, "HH:mm a")
        pressure.text = context?.getString(R.string.w_pressure, currentDay.pressure)
//        time.text = getLocalDate()

        val adapter = view?.findViewById<RecyclerView>(R.id.rv_forecast)?.adapter as ForecastAdapter
        adapter.submitList(data.list)
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

        location.setOnClickListener {
            searchCity.visibility = View.VISIBLE
            location.visibility = View.GONE
        }

        searchCity.setEndIconOnClickListener {
            location.visibility = View.VISIBLE
            searchCity.visibility = View.GONE
            loadWeather(searchCity.editText?.text.toString().trim())
        }
    }
}