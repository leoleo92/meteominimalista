package com.leonardo.weatherapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardo.weatherapp.databinding.HomeAcitivityBinding
import com.leonardo.weatherapp.repo.WeatherRepo
import com.leonardo.weatherapp.view.HomeWeatherFragment
import com.leonardo.weatherapp.view.WeatherRecyclerViewAdapter
import com.leonardo.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    //private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = HomeAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


/*
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHost.findNavController()

        //setupActionBarWithNavController(navController)
        */

    }


}