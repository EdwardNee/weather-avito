package com.nieduard.weather_avito.adapters

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

class ForecastAdapter(private val onClickCard: (item: Lst) -> Unit) :
    ListAdapter<Lst, ForecastViewHolder>(ForecastDiffCallback()) {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val holderBinding: ViewHolderWeatherBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.view_holder_weather, parent, false)
        return ForecastViewHolder(holderBinding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickCard)
    }
}

class ForecastViewHolder(private val holderBinding: ViewHolderWeatherBinding) :
    RecyclerView.ViewHolder(holderBinding.root) {

    fun onBind(item: Lst, onClickCard: (item: Lst) -> Unit) {
//        Glide.with(holderBinding.holderImage.context)
//            .load("http://developer.alexanderklimov.ru/android/images/android_cat.jpg")
//            .into(holderBinding.holderImage)
        holderBinding.holderDate.text =
            holderBinding.root.context.getString(R.string.str_val, TimeHelper.CITY_OFFSET?.let {
                TimeHelper.dateFromUnix(
                    item.dt,
                    it, "EEE, dd MMM"
                )
            })
        holderBinding.holderDegree.text = holderBinding.root.context.getString(
            R.string.w_degree,
            (item.temp.day - 273.15).roundToInt()
        )
        holderBinding.root.setOnClickListener { onClickCard(item) }
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