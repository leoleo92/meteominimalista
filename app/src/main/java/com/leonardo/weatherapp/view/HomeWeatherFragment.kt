package com.leonardo.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardo.weatherapp.R
import com.leonardo.weatherapp.databinding.HomeWeatherScreenFragmentBinding
import com.leonardo.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


const val TAG = "HomeWeatherFragment"

@AndroidEntryPoint
class HomeWeatherFragment : Fragment(R.layout.home_weather_screen_fragment) {

    val weatherViewModel: WeatherViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = HomeWeatherScreenFragmentBinding.bind(view)
        val weatherAdapter = WeatherRecyclerViewAdapter()




        //Set the widgets
        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = weatherAdapter
                setHasFixedSize(true)
            }

            cityName.setOnClickListener {
                val  action = HomeWeatherFragmentDirections.actionHomeWeatherFragment2ToSearchWeatherFragment()
                findNavController().navigate(action)
            }

        }

        CoroutineScope(Dispatchers.Main).launch {
            weatherViewModel.getCity().collect {
                if (it == null) {

                    Log.i(TAG, "We don't have a city name!")

                    //Asks viewmodel to launch a search screen
                    weatherViewModel.setCity("London")
                } else {

                    Log.i(TAG, "We got a city name!")
                    binding.cityName.text = it

                }
            }

        }

        //This retrieve the data
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner, {

            Log.i(TAG, "The list has ${it?.size} items")
            weatherAdapter.submitList(it)


        })

    }





/*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    val binding = HomeWeatherScreenFragmentBinding.bind(view)
    val weatherAdapter = WeatherRecyclerViewAdapter()

    //Set the widgets
    binding.apply {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
            setHasFixedSize(true)
        }

        cityName.setOnClickListener {

        }

    }

    CoroutineScope(Dispatchers.Main).launch {
        weatherViewModel.getCity().collect {
            if (it == null) {

                Log.i(TAG, "We don't have a city name!")

                //Asks viewmodel to launch a search screen
                weatherViewModel.setCity("London")
            } else {

                Log.i(TAG, "We got a city name!")
                binding.cityName.text = it

            }
        }

    }

    //This retrieve the data
    weatherViewModel.weatherResponse.observe(viewLifecycleOwner, {

        Log.i(TAG, "The list has ${it?.size} items")
        weatherAdapter.submitList(it)


    })
}*/

}