package com.nieduard.weather_avito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nieduard.weather_avito.viewmodels.IShowToast
import com.nieduard.weather_avito.views.IOpenNewCityFragment
import com.nieduard.weather_avito.views.WeatherFragment

enum class FragmentSwitch {
    REPLACE, ADD
}

class MainActivity : AppCompatActivity(), IOpenNewCityFragment, IShowToast {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            moveToFragment(WeatherFragment(), FragmentSwitch.REPLACE)
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

    override fun onOpenNewCityFragment() {
        moveToFragment(NewCityFragment(), FragmentSwitch.ADD)
    }

    override fun onShowToast() {
        Toast.makeText(this, "haha", Toast.LENGTH_LONG).show()
    }
}