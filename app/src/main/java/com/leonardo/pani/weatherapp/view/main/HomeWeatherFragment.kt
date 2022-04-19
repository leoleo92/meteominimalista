package com.leonardo.pani.weatherapp.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.HomeWeatherScreenFragmentBinding
import com.leonardo.pani.weatherapp.model.City
import com.leonardo.pani.weatherapp.view.citysearch.SearchWeatherFragment
import dagger.hilt.android.AndroidEntryPoint


const val TAG = "HomeWeatherFragment"

@AndroidEntryPoint
class HomeWeatherFragment : Fragment(R.layout.home_weather_screen_fragment), ClickedLastItem {

    val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var weatherRecyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setBackStackEntryObserver()


        val binding = HomeWeatherScreenFragmentBinding.bind(view)
        val weatherAdapter = WeatherRecyclerViewAdapter(this)


        //Set the widgets
        binding.apply {

            weatherRecyclerView = recyclerView


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
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner) { city ->
            //It means we have infos from a city
            if (city.cityName.isNotEmpty()) {

                val fivedayForecastList = city.fivedayForecast?.DailyForecasts
                val currentConditions = city.currentCondition


                weatherAdapter.submitList(fivedayForecastList)

                //Set the current temperature in celsius for the TextView
                val currentTempCelsius =
                    currentConditions?.get(0)?.Temperature?.Metric?.Value?.toInt()
                binding.cityTemperature.text = "$currentTempCelsius"


                binding.cityName.text = city.cityName
                binding.currentConditionText.text = city.currentCondition?.get(0)?.WeatherText
                binding.currentConditionText.isSelected = true

            } else {
                Log.i(TAG, "There is no city saved in the preferences. Go to the set Location screen")
                weatherViewModel.goToSetLocationScreen()
            }

        }


        //Here we listen to the viewmodel channel events
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            weatherViewModel.receiveFromWeatherViewModelChannel.collect { event ->
                when (event) {

                    is WeatherViewModel.WeatherUseCases.NavigateToSetLocationScreen -> {
                        val action =
                            HomeWeatherFragmentDirections.actionHomeWeatherFragmentToSearchWeatherFragment()
                        findNavController().navigate(action)
                    }

                    is WeatherViewModel.WeatherUseCases.NavigateToTheErrorPage -> {
                        val action =
                            HomeWeatherFragmentDirections.actionHomeWeatherFragmentToErrorPage()
                        findNavController().navigate(action)
                    }


                }
            }
        }

    }


    //This method is used to retrieve the city name from the location screen. The name is then sent to the viewmodel which will then connect to the API to
    //retrieve the weather forecasts
    private fun setBackStackEntryObserver() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<City>(SearchWeatherFragment.KEY_CITY)
            ?.observe(viewLifecycleOwner) { city ->

                weatherViewModel.checkTheReceivedCity(city)

                savedStateHandle.remove<String>(SearchWeatherFragment.KEY_CITY)

            }
    }


    //Callback from the recyclerview adapter:
    // If the user clicks the last item of the list, scroll the view to the bottom so that the user can properly see all of the information
    override fun scrollViewToLastItem() {
        val adpter = weatherRecyclerView.adapter

        if (adpter != null) {
            val itemToScrollTo = adpter.itemCount - 1
            weatherRecyclerView.scrollToPosition(itemToScrollTo)
        }
    }


}