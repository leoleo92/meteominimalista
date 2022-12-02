package com.leonardo.pani.weatherapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.leonardo.pani.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ErrorPage: Fragment(R.layout.error_page_layout) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                activity?.finish()

                //todo
                //activity?.moveTaskToBack(true)
                //activity?.finish()
               /*val intent = Intent(activity, SplashScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("EXIT", true)
                startActivity(intent)

                activity!!.finish()*/
            }

        })
    }
}