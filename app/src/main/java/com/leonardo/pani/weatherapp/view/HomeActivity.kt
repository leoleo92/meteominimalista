package com.leonardo.pani.weatherapp.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.HomeAcitivityBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //Check if the User is connected to Internet
        if(isOnline(applicationContext)) {

            inflateMainActivity()
        }else {
            //If not, redirect the user to an error page and ask him/her to activate the connection
            setContentView(R.layout.lack_connection_layout)
            val tryAgainBtn = findViewById<Button>(R.id.retry_btn)
            tryAgainBtn.setOnClickListener {

                if(isOnline(applicationContext)) {

                    inflateMainActivity()
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

}