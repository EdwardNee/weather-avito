package com.nieduard.weather_avito.views

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.adapters.ForecastAdapter
import com.nieduard.weather_avito.databinding.FragmentWeatherBinding
import com.nieduard.weather_avito.helpers.LocationPermissionHelper
import com.nieduard.weather_avito.model.Coords
import com.nieduard.weather_avito.model.WeatherModel
import com.nieduard.weather_avito.modelfactories.LocationModelFactory
import com.nieduard.weather_avito.modelfactories.WeatherModelFactory
import com.nieduard.weather_avito.utils.IDaySelected
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.utils.TimeHelper
import com.nieduard.weather_avito.viewmodels.LocationViewModel
import com.nieduard.weather_avito.viewmodels.WeatherViewModel
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var binding: FragmentWeatherBinding? = null

    private val viewModel: WeatherViewModel by viewModels { WeatherModelFactory(showToastListener) }
    private lateinit var locationViewModel: LocationViewModel

    private var showToastListener: IShowToast? = null
    private var daySelectedListener: IDaySelected? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IShowToast)
            showToastListener = context

        if (context is IDaySelected)
            daySelectedListener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationViewModel =
            ViewModelProvider(this, LocationModelFactory()).get(LocationViewModel::class.java)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        initViews()
        return binding!!.root
    }

    private fun getPermissionAndLocation() {
        val hasLocPerm = LocationPermissionHelper.hasLocationPermission(requireContext())
        val isLocEnabled = LocationPermissionHelper.isLocationEnabled(requireContext())

        if (!hasLocPerm) {
            LocationPermissionHelper.requestPermissions(requireActivity())
        }

        if (!isLocEnabled) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        if (hasLocPerm && isLocEnabled) {
            val data = LocationPermissionHelper.getLocation(requireActivity())
            locationViewModel.initLocation(data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvForecast?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            val adapter = ForecastAdapter { day -> daySelectedListener?.onDaySelected(day) }
            this.adapter = adapter
        }

        locationViewModel.location.observe(viewLifecycleOwner, { location ->
            loadWeather(location)
        })
    }

    override fun onDetach() {
        super.onDetach()
        showToastListener = null
        daySelectedListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Calls loadData in [WeatherViewModel] with the given name of [city].
     */
    private fun loadWeather(city: String) {
        viewModel.loadData(city)
        observeWeatherModel()
    }

    /**
     * Calls loadData in [WeatherViewModel] with the given [coordinates].
     */
    private fun loadWeather(coordinates: Coords?) {
        if (coordinates != null) {
            viewModel.loadData(coordinates.lat, coordinates.lon)
            observeWeatherModel()
        }
    }

    var played = false
    /**
     * Calls observe on [WeatherViewModel] viewModel.
     */
    private fun observeWeatherModel() {
        viewModel.weatherData.observe(viewLifecycleOwner, { d ->
            bindUI(d)
//            if(!played){
//                val height = Resources.getSystem().displayMetrics.heightPixels
//                val width = Resources.getSystem().displayMetrics.widthPixels
//                val transValue = -height / 2f + height / 12f
//                binding?.wLocation?.animate()?.setDuration(1000)
//                    ?.translationY(transValue)
//                    ?.scaleX(0.8f)?.scaleY(0.8f)?.withEndAction {
//                        binding?.help1?.animate()?.setDuration(500)?.alpha(0f)
//                        binding?.help2?.animate()?.setDuration(500)?.alpha(0f)
//                    }
//                binding?.etSearchLoc?.animate()?.setDuration(0)
//                    ?.translationY(transValue)
//                    ?.scaleX(0.9f)?.scaleY(0.9f)
//
//                binding?.imageLoc?.animate()?.setDuration(1000)
//                    ?.translationX(width / 3f - binding?.imageLoc?.translationX!!)
//                    ?.translationY(transValue - 20f)
//                played = true
//            }
        })
    }

    /**
     * Binds UI with data, loaded from openWeatherMap api.
     */
    private fun bindUI(data: WeatherModel) {
        val currentDay = data.list[0]
        //Converting from Kelvin to Celsius.
        binding?.wDegrees?.text =
            context?.getString(R.string.w_degree, (currentDay.temp.day.minus(273.15)).toInt())
        binding?.w1Info?.text = context?.getString(R.string.w_wind_v, currentDay.speed.toInt())
        binding?.w2Info?.text =
            context?.getString(R.string.str_val, currentDay.humidity.toString() + "%")
        binding?.wLocation?.text = data.city.name
        binding?.w4Info?.text =
            TimeHelper.dateFromUnix(currentDay.sunrise, data.city.timezone, "HH:mm a")
        binding?.w3Info?.text = context?.getString(R.string.w_pressure, currentDay.pressure)
        binding?.wTime?.text = context?.getString(
            R.string.str_val, TimeHelper.dateFromUnix(
                (System.currentTimeMillis() / 1000.0).roundToInt(),
                data.city.timezone,
                "dd MMM / HH:mm a"
            )
        )
        binding?.w5Info?.text = context?.getString(
            R.string.str_val,
            TimeHelper.dateFromUnix(currentDay.sunset, data.city.timezone, "HH:mm a")
        )
        binding?.w6Info?.text =
            context?.getString(R.string.str_val, (currentDay.pop * 100).toString() + "%")


        val adapter = binding?.rvForecast?.adapter as ForecastAdapter
        adapter.submitList(data.list.drop(1))
    }

    /**
     * Initializes the components.
     */
    private fun initViews() {




        binding?.wLocation?.setOnClickListener {
            binding?.etSearchLoc?.visibility = View.VISIBLE
            binding?.wLocation?.visibility = View.GONE
        }

        binding?.etSearchLoc?.setEndIconOnClickListener {
            binding?.wLocation?.visibility = View.VISIBLE
            binding?.etSearchLoc?.visibility = View.GONE
           
            loadWeather(binding?.etSearchLoc?.editText?.text.toString().trim())
        }

        binding?.wCurrPos?.setOnClickListener {
            getPermissionAndLocation()
        }
    }
}