package com.leonardo.pani.weatherapp.view

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.HomeAcitivityBinding
import com.leonardo.pani.weatherapp.databinding.SplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.i("HomeActivity","Homeactivity created")



        //Check if the User is connected to Internet
        if(isOnline(applicationContext)) {

            inflateMainActivity()
        }else {
            //If not, redirect the user to an error page and ask him/her to activate the connection
            setContentView(R.layout.lack_connection_layout)
            val tryAgainBtn = findViewById<LottieAnimationView>(R.id.retry_btn)
            tryAgainBtn.setOnClickListener {


                tryAgainBtn.speed = 1F
                tryAgainBtn.playAnimation()

                if(isOnline(applicationContext)) {

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {

                        inflateMainActivity()

                    }, 2000)



                }

            }
        }

    }






    fun inflateMainActivity() {
        val binding = HomeAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


    override fun onResume() {
        super.onResume()
        Log.i("HomeActivity","Homeactivity resumed")

    }
}