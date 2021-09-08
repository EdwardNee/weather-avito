package com.nieduard.weather_avito.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.databinding.ViewHolderWeatherBinding
import com.nieduard.weather_avito.model.Lst
import com.nieduard.weather_avito.utils.TimeHelper
import kotlin.math.roundToInt

class ForecastAdapter : ListAdapter<Lst, ForecastViewHolder>(ForecastDiffCallback()) {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val holderBinding: ViewHolderWeatherBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.view_holder_weather, parent, false)

        Log.d("TAG_HOLDER", holderBinding.toString())
        return ForecastViewHolder(holderBinding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }
}

class ForecastViewHolder(private val holderBinding: ViewHolderWeatherBinding) :
    RecyclerView.ViewHolder(holderBinding.root) {

    fun onBind(item: Lst) {
        holderBinding.holderDate.text = TimeHelper.CITY_OFFSET?.let {
            TimeHelper.dateFromUnix(
                item.dt,
                it, "dd MMM, EEE"
            )
        }
        holderBinding.holderDegree.text = (item.temp.day - 273.15).roundToInt().toString()
    }
}

/**
 * Callback class for updating difference data of forecast.
 */
class ForecastDiffCallback : DiffUtil.ItemCallback<Lst>() {
    override fun areItemsTheSame(oldItem: Lst, newItem: Lst): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Lst, newItem: Lst): Boolean {
        return oldItem == newItem
    }
}