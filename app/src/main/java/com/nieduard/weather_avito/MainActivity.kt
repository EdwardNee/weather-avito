package com.nieduard.weather_avito

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nieduard.weather_avito.helpers.LocationPermissionHelper
import com.nieduard.weather_avito.modelfactories.LocationModelFactory
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.viewmodels.LocationViewModel
import com.nieduard.weather_avito.views.WeatherFragment
import okhttp3.OkHttpClient

enum class FragmentSwitch {
    REPLACE, ADD
}

class MainActivity : AppCompatActivity(), IShowToast {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationViewModel =
            ViewModelProvider(this, LocationModelFactory()).get(LocationViewModel::class.java)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (savedInstanceState == null)
            moveToFragment(WeatherFragment(), FragmentSwitch.REPLACE)

        LocationPermissionHelper.requestPermissions(this)

//        getLastLocation()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationPermissionHelper.LOCATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //get the location
                locationViewModel.initLocation(LocationPermissionHelper.getLocation(this))
            }
        }
    }

    /**
     * Moving to given fragment.
     */
    private fun moveToFragment(fragment: Fragment, replaceOrAdd: FragmentSwitch) {
        when (replaceOrAdd) {
            FragmentSwitch.ADD -> {
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.wrapper_main, fragment)
                    addToBackStack(null)
                    commit()
                }
            }

            FragmentSwitch.REPLACE -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.wrapper_main, fragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onShowToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }
}