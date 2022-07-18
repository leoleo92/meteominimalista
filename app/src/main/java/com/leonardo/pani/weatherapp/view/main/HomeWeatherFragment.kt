package com.leonardo.pani.weatherapp.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.HomeWeatherScreenFragmentBinding
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.utils.Consts.ICON_IDS_CURRENT_WEATHER_API
import com.leonardo.pani.weatherapp.view.citysearch.SearchWeatherFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


const val TAG = "HomeWeatherFragment"



@AndroidEntryPoint
class HomeWeatherFragment : Fragment(R.layout.home_weather_screen_fragment) {

    val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var weatherRecyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setBackStackEntryObserver()


        val binding = HomeWeatherScreenFragmentBinding.bind(view)


        //Set the widgets
        binding.apply {

            weatherRecyclerView = recyclerView


            recyclerView.apply {

                val ltManager = LinearLayoutManager(context)
                ltManager.orientation = RecyclerView.HORIZONTAL

                layoutManager = ltManager

                setHasFixedSize(true)


            }

            searchCityIcn.setOnClickListener {

                weatherViewModel.goToSearchCityFragment()

            }

        }


        //This retrieve the Five day forecast list and the current conditions of the city
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner) { weatherForecast ->



            val currentConditions = weatherForecast.current
            val sevenDaysForecast = weatherForecast.daysForecast


            val mainIconToLoad = ICON_IDS_CURRENT_WEATHER_API.get(weatherForecast.current.weather.get(0).icon)
            Log.i(TAG,"the id is ${weatherForecast.current.weather.get(0).icon}")

            Glide.with(binding.root)
                .load(mainIconToLoad)
                .into(binding.currentWeatherIcon)


            binding.cityTemperature.text = "${currentConditions.temp.toInt()}"

            val cityName = weatherForecast.cityName.split(',')
            binding.maxDayTemp.text = "${weatherForecast.daily.get(0).temp.max.toInt().toString()}°"
            binding.minDayTemp.text = "${weatherForecast.daily.get(0).temp.min.toInt().toString()}°"
            binding.cityName.text = cityName.get(0)
            binding.currentConditionText.text = weatherForecast.current.weather.get(0).description.capitalize()
            binding.currentConditionText.isSelected = true
            binding.perceivedTemp.text = "${weatherForecast.current.feels_like.toInt().toString()}°"
            binding.uviLevel.text = weatherForecast.current.uvi.toInt().toString()
            binding.humidity.text = "${weatherForecast.current.humidity.toString()}%"
            binding.popPercentage.text = "${weatherForecast.daily.get(0).pop * 100 / 1} %"

            if(sevenDaysForecast != null) {
                weatherRecyclerView.adapter = WeatherRecyclerViewAdapter(sevenDaysForecast)
            }


            val time = formattedTime(weatherForecast.current.sunrise, weatherForecast.timezone)

            val hour = time.split(':').get(0).toInt()
            val degrees = (hour * 360 / 12).toFloat()
            val rotateAnimation = RotateAnimation(
                0.0f, degrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1f
            )
            rotateAnimation.setFillAfter(true)
            rotateAnimation.setDuration(2000)
            rotateAnimation.setRepeatCount(0)
            rotateAnimation.fillAfter = true
            rotateAnimation.setInterpolator(LinearInterpolator())

            rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {

                    //(x2,y2)=(x1+l⋅cos(a),y1+l⋅sin(a))
                    binding.sunriseTime.visibility = View.VISIBLE

                    binding.sunriseTime.text = time

                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
            binding.sunriseLineImage.startAnimation(rotateAnimation)


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


    fun formattedTime(time: Long, zone: String, format: String = "HH:mm"): String {
        // parse the time zone
        val zoneId = ZoneId.of(zone)
        // create a moment in time from the given timestamp (in seconds!)
        val instant = Instant.ofEpochSecond(time)
        // define a formatter using the given pattern and a Locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ITALIAN)
        // then make the moment in time consider the zone and return the formatted String
        return instant.atZone(zoneId).format(formatter)
    }


    //This method is used to retrieve the city name from the location screen. The name is then sent to the viewmodel which will then connect to the API to
    //retrieve the weather forecasts
    private fun setBackStackEntryObserver() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<CityNameAndCoordinates>(SearchWeatherFragment.KEY_CITY_FEATURE)
            ?.observe(viewLifecycleOwner) { cityBasicInfo ->

                weatherViewModel.requestWeatherForecastAndCurrentConditions(cityBasicInfo)

                savedStateHandle.remove<String>(SearchWeatherFragment.KEY_CITY_FEATURE)

            }
    }


}