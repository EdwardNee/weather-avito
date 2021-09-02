package com.nieduard.weather_avito.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nieduard.weather_avito.R
import com.nieduard.weather_avito.model.Lst
import com.nieduard.weather_avito.utils.TimeHelper
import kotlin.math.roundToInt

class ForecastAdapter : ListAdapter<Lst, ForecastViewHolder>(ForecastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_weather, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }
}


class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val date: TextView = itemView.findViewById(R.id.holder_date)
    private val temp: TextView = itemView.findViewById(R.id.holder_degree)

    fun onBind(item: Lst) {
//        val context = itemView.context
        date.text = TimeHelper.CITY_OFFSET?.let {
            TimeHelper.dateFromUnix(item.dt,
                it, "dd MMM, EEE")
        }
        temp.text = (item.temp.day - 273.15).roundToInt().toString()
    }
}

/**
 * Callback class for updating difference data of Actors.
 */
class ForecastDiffCallback : DiffUtil.ItemCallback<Lst>() {
    override fun areItemsTheSame(oldItem: Lst, newItem: Lst): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Lst, newItem: Lst): Boolean {
        return oldItem == newItem
    }
}