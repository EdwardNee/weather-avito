package com.nieduard.weather_avito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            moveToFragment(WeatherFragment())
    }

    /**
     * Moving to given fragment.
     */
    private fun moveToFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.wrapper_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}