package com.nieduard.weather_avito

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nieduard.weather_avito.helpers.LocationPermissionHelper
import com.nieduard.weather_avito.model.Lst
import com.nieduard.weather_avito.modelfactories.LocationModelFactory
import com.nieduard.weather_avito.utils.IDaySelected
import com.nieduard.weather_avito.utils.IShowToast
import com.nieduard.weather_avito.viewmodels.LocationViewModel
import com.nieduard.weather_avito.views.DayDetailBottomSheet
import com.nieduard.weather_avito.views.WeatherFragment

enum class FragmentSwitch {
    REPLACE, ADD
}

class MainActivity : AppCompatActivity(), IShowToast, IDaySelected {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationViewModel =
            ViewModelProvider(this, LocationModelFactory()).get(LocationViewModel::class.java)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState == null)
            moveToFragment(WeatherFragment(), FragmentSwitch.ADD)

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
//                if (locationViewModel.location.value == null)
                    locationViewModel.initLocation(LocationPermissionHelper.getLocation(this))
                Log.d("main_act_tag", "HEHE")
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
                   // addToBackStack(null)
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

    override fun onDaySelected(day: Lst) {
//        moveToFragment(DayDetailBottomSheet.newInstance(day), FragmentSwitch.REPLACE)
        val bottomSheet = DayDetailBottomSheet.newInstance(day)
        bottomSheet.show(supportFragmentManager, "bottom_sheet_tag")
    }
}