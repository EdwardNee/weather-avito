package com.nieduard.weather_avito.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

object TimeHelper {
    var CITY_OFFSET: Int? = null

    /**
     * Converts given unix [time] to human readable time in [format].
     */
    fun dateFromUnix(time: Int, offset: Int, format: String): String {
        return try {
            val utc = (time).toLong() - (TimeZone.getDefault().rawOffset / 1000.0).roundToLong()
            val cityTime = utc + offset

            SimpleDateFormat(format, Locale.getDefault()).format(Date(cityTime * 1000))
        } catch (e: Exception) {
            e.toString()
        }
    }
}

