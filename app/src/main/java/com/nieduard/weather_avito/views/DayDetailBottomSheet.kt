package com.nieduard.weather_avito.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.databinding.FragmentDayDetailBinding
import com.nieduard.weather_avito.model.Lst
import com.nieduard.weather_avito.utils.TimeHelper
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 * Use the [DayDetailBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayDetailBottomSheet : BottomSheetDialogFragment() {

    private var binding: FragmentDayDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dayInfo = arguments?.getSerializable(PARAM_DAY) as? Lst ?: return

        binding?.dMorn?.text =
            context?.getString(R.string.w_degree, (dayInfo.temp.morn - 273.15).roundToInt())
        binding?.dDay?.text =
            context?.getString(R.string.w_degree, (dayInfo.temp.day - 273.15).roundToInt())
        binding?.dNight?.text =
            context?.getString(R.string.w_degree, (dayInfo.temp.night - 273.15).roundToInt())
        binding?.dTextDesc?.text =
            context?.getString(R.string.str_val, dayInfo.weather[0].description)
        binding?.dWindTv?.text = context?.getString(R.string.w_wind_v, dayInfo.speed.toInt())
        binding?.dHumidTv?.text = context?.getString(R.string.str_val, dayInfo.humidity.toString())
        binding?.dSunrTv?.text = context?.getString(
            R.string.str_val,
            TimeHelper.dateFromUnix(dayInfo.sunrise, TimeHelper.CITY_OFFSET!!, "HH:mm")
        )
        binding?.dSunsTv?.text = context?.getString(
            R.string.str_val,
            TimeHelper.dateFromUnix(dayInfo.sunset, TimeHelper.CITY_OFFSET!!, "HH:mm")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val PARAM_DAY = "day_parameter"

        @JvmStatic
        fun newInstance(day: Lst) =
            DayDetailBottomSheet().also {
                val arg = bundleOf(PARAM_DAY to day)
                it.arguments = arg
            }
    }
}