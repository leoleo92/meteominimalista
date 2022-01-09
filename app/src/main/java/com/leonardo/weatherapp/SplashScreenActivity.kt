package com.leonardo.weatherapp

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.opengl.ETC1.getWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.*
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.leonardo.weatherapp.databinding.SplashScreenBinding
import com.leonardo.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = SplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        )


        //Use this if you want the fullscreen, but remember, this also hide the notification bar etc.. it's better if a set the primary toolbar color to black.
        /*WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }*/


        binding.theLottie.animate().translationX(-1000f).setDuration(1500).setStartDelay(3000)
        binding.appTitleSplashScreen.fadeIn(1300)
        binding.appTitleSplashScreen.animate().translationX(1000f).setDuration(1500)
            .setStartDelay(3000)




        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            //TODO start here the mainacitivity
            startActivity(Intent(this, HomeActivity::class.java))

        }, 4000)

        //Switch style for night mode/day mode
        when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> binding.theLottie.animate().setDuration(1000)
                .setStartDelay(4000)
            //app:lottie_autoPlay="true"

            Configuration.UI_MODE_NIGHT_NO -> Log.i("Theme", "Night mode off!")

            else -> ""

        }



    }

    inline fun View.fadeIn(durationMillis: Long = 250) {
        this.startAnimation(AlphaAnimation(0F, 1F).apply {
            duration = durationMillis
            fillAfter = true
        })
    }

}