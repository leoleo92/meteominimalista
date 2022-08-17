package com.leonardo.pani.weatherapp.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.*
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.SplashScreenBinding
import com.leonardo.pani.weatherapp.view.main.HomeWeatherFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.splash_screen) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SplashScreenBinding.bind(view)

        hideSystemUI(binding)




        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            val action =
                SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeWeatherFragment()
            findNavController().navigate(action)

        }, 4000)

    }



    private fun hideSystemUI(view: SplashScreenBinding) {

        val window = activity?.window
        if (window != null) {

            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, view.root).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }


}