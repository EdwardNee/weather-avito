package com.nieduard.weather_avito.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.nieduard.weather_avito.model.Coord
import com.nieduard.weather_avito.model.Coords

object LocationPermissionHelper {
    const val LOCATION_PERMISSION_CODE = 37
    private var result: Coords? = null

    /**
     * Checks if the user grant to access [Manifest.permission.ACCESS_COARSE_LOCATION]
     * and [Manifest.permission.ACCESS_FINE_LOCATION].
     */
    fun hasLocationPermission(context: Context): Boolean {
        return (
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                )
    }

    /**
     * Checks if the user turned on location from the settings.
     */
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /**
     * Requests permissions for [Manifest.permission.ACCESS_COARSE_LOCATION]
     * and [Manifest.permission.ACCESS_FINE_LOCATION].
     */
    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSION_CODE
        )
    }

    @SuppressLint("MissingPermission")
    fun getLocation(activity: Activity): Coords? {

        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
            val location: Location? = task.result
            if (location != null) {
                //FOUND LOCATION
                result = Coords(location.latitude, location.longitude)
            }
        }
        return result
    }
}