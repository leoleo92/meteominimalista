package com.leonardo.weatherapp.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leonardo.weatherapp.R
import com.leonardo.weatherapp.databinding.ErrorPageLayoutBinding
import com.leonardo.weatherapp.databinding.HomeWeatherScreenFragmentBinding
import com.leonardo.weatherapp.model.City
import com.leonardo.weatherapp.model.WeatherUseCases
import com.leonardo.weatherapp.utils.Consts
import com.leonardo.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


const val TAG = "HomeWeatherFragment"

@AndroidEntryPoint
class HomeWeatherFragment : Fragment(R.layout.home_weather_screen_fragment) {

    val weatherViewModel: WeatherViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setBackStackEntryObserver()


        val binding = HomeWeatherScreenFragmentBinding.bind(view)
        val weatherAdapter = WeatherRecyclerViewAdapter()

        //Set the widgets
        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = weatherAdapter
                setHasFixedSize(true)
            }

            searchCityIcn.setOnClickListener {

                weatherViewModel.goToSetLocationScreen()

            }


        }


        //This retrieve the Five day forecast list and the current conditions of the city
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner, { city ->

            //It means we have infos from a city
            if (city.cityName.isNotEmpty()) {

                val fivedayForecastList = city.fivedayForecast?.DailyForecasts
                val currentConditions = city.currentCondition


                Log.i(TAG, "The list has a size of ${fivedayForecastList?.size}")
                weatherAdapter.submitList(fivedayForecastList)

                //Set the current temperature in celsius for the TextView
                val currentTempCelsius =
                    currentConditions?.get(0)?.Temperature?.Metric?.Value?.toInt()
                binding.cityTemperature.text = "$currentTempCelsius"
                binding.cityName.text = city.cityName
                binding.currentConditionText.text = city.currentCondition?.get(0)?.WeatherText
                binding.currentConditionText.isSelected = true
                val iconId = city.currentCondition?.get(0)?.WeatherIcon

            } else {
                Log.i(TAG, "There is no city saved in the preferences")
                weatherViewModel.goToSetLocationScreen()
            }

        })


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            weatherViewModel.receiveFromWeatherViewModelChannel.collect { event ->
                when (event) {

                    is WeatherUseCases.NavigateToSetLocationScreen -> {
                        val action =
                            HomeWeatherFragmentDirections.actionHomeWeatherFragmentToSearchWeatherFragment()
                        findNavController().navigate(action)
                    }

                    is WeatherUseCases.Sunny -> {
                        binding.constraintLayout.setBackgroundResource(R.drawable.sunny)
                    }
                    is WeatherUseCases.Cloudy -> {
                        binding.constraintLayout.setBackgroundResource(R.drawable.cloudy)
                    }
                    is WeatherUseCases.Snowy -> {
                        binding.constraintLayout.setBackgroundResource(R.drawable.snow)
                    }
                    is WeatherUseCases.Rainy -> {
                        binding.constraintLayout.setBackgroundResource(R.drawable.rainy)
                    }

                }
            }
        }

    }


    private fun setBackStackEntryObserver() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<City>(SearchWeatherFragment.KEY_CITY)
            ?.observe(viewLifecycleOwner) { city ->
                Log.d(TAG, "In the Back stack Observer, the city isnull? ${city == null}")
                if (city != null) {

                    savedStateHandle.remove<String>(SearchWeatherFragment.KEY_CITY)

                    //Test
                    Log.i(TAG, "The user searched the weatehr for ${city.currentCondition}")
                    // Handle the textValue result
                    weatherViewModel.getInfoFromNewCitySearch(city.cityApiLocation, city.cityName)
                } else {
                    val action = HomeWeatherFragmentDirections.actionHomeWeatherFragmentToErrorPage()
                    findNavController().navigate(action)
                }
            }
    }




}