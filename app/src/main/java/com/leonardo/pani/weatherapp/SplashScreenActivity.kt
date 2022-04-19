package com.leonardo.pani.weatherapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.*
import com.leonardo.pani.weatherapp.databinding.SplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getBooleanExtra("LOGOUT", false)) {

            finish()
        } else {


            val binding = SplashScreenBinding.inflate(layoutInflater)

            setContentView(binding.root)
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            )


            binding.theLottie.animate().translationX(-1000f).setDuration(1500).setStartDelay(3000)
            binding.appTitleSplashScreen.fadeIn(1300)
            binding.appTitleSplashScreen.animate().translationX(1000f).setDuration(1500)
                .setStartDelay(3000)



            Handler(Looper.getMainLooper()).postDelayed(Runnable {

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

    }

    inline fun View.fadeIn(durationMillis: Long = 250) {
        this.startAnimation(AlphaAnimation(0F, 1F).apply {
            duration = durationMillis
            fillAfter = true
        })
    }


}