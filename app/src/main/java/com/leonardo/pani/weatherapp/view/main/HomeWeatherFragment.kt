package com.leonardo.pani.weatherapp.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.HomeWeatherScreenFragmentBinding
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.model.DailyConditions
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import com.leonardo.pani.weatherapp.utils.Consts.ICON_IDS_CURRENT_WEATHER_API
import com.leonardo.pani.weatherapp.view.citysearch.SearchWeatherFragment
import com.leonardo.pani.weatherapp.view.weather_Detail.DetailedFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


const val TAG = "HomeWeatherFragment"


@AndroidEntryPoint
class HomeWeatherFragment : Fragment(R.layout.home_weather_screen_fragment), OnDayClickedListener {

    private lateinit var weatherRecyclerView: RecyclerView
    private lateinit var binding: HomeWeatherScreenFragmentBinding

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeWeatherScreenFragmentBinding.bind(view)

        setBackStackEntryObserver()


        //Set the recyclerview
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


        //This retrieve the forecasts list and the current conditions of the city and sets the various fragment's widget with the retrieved information
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner) { weatherForecast ->
            setTheView(binding, weatherForecast)

        }


        //Here we listen to the viewmodel channel events
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            weatherViewModel.receiveFromWeatherViewModelChannel.collect { event ->
                when (event) {

                    is WeatherViewModel.WeatherUseCases.NavigateToSetLocationScreen -> {
                        val action =
                            HomeWeatherFragmentDirections.actionHomeWeatherFragmentToSearchWeatherFragment()
                        //findNavController().graph.setStartDestination(R.id.homeWeatherFragment) // Little bit tricky solution :)
                        findNavController().navigate(action)
                    }


                    is WeatherViewModel.WeatherUseCases.NavigateToDetailWeatherScreen -> {
                        val action =
                            HomeWeatherFragmentDirections.actionHomeWeatherFragmentToDetailedFragment(
                                event.day
                            )
                        //findNavController().saveState()
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


    fun setTheView(
        binding: HomeWeatherScreenFragmentBinding,
        weatherForecast: WeatherForecast,
        playAnimation: Boolean = true
    ) {

        val currentConditions = weatherForecast.current
        val sevenDaysForecast = weatherForecast.daysForecast
        weatherRecyclerView = binding.recyclerView

        if (sevenDaysForecast != null) {
            weatherRecyclerView.adapter = WeatherRecyclerViewAdapter(this, sevenDaysForecast)
        }

        binding.apply {


            val mainIconToLoad =
                ICON_IDS_CURRENT_WEATHER_API.get(weatherForecast.current.weather.get(0).icon)

            Glide.with(root)
                .load(mainIconToLoad)
                .into(currentWeatherIcon)






            recyclerView.apply {

                val ltManager = LinearLayoutManager(context)
                ltManager.orientation = RecyclerView.HORIZONTAL

                layoutManager = ltManager

                setHasFixedSize(true)


            }

            searchCityIcn.setOnClickListener {

                weatherViewModel.goToSearchCityFragment()

            }


            binding.cityTemperature.text = "${currentConditions.temp.toInt()}"

            val name = weatherForecast.cityName.split(',')
            maxDayTemp.text = "${weatherForecast.daily.get(0).temp.max.toInt().toString()}°"
            minDayTemp.text = "${weatherForecast.daily.get(0).temp.min.toInt().toString()}°"
            cityName.text = name.get(0)
            currentConditionText.text =
                weatherForecast.current.weather.get(0).description.capitalize()
            currentConditionText.isSelected = true
            perceivedTemp.text = "${weatherForecast.current.feels_like.toInt().toString()}°"
            uviLevel.text = weatherForecast.current.uvi.toInt().toString()
            humidity.text = "${weatherForecast.current.humidity.toString()}%"
            popPercentage.text = "${(weatherForecast.daily.get(0).pop * 100 / 1).toInt()} %"


            val time = formattedTime(weatherForecast.current.sunrise, weatherForecast.timezone)


            val animationDuration = if (playAnimation) 2000L else 0

            val hour = time.split(':').get(0).toInt()
            val degrees = (hour * 360 / 12).toFloat()
            val rotateAnimation = RotateAnimation(
                0.0f, degrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1f
            )
            rotateAnimation.setFillAfter(true)
            rotateAnimation.setDuration(animationDuration)
            rotateAnimation.setRepeatCount(0)
            rotateAnimation.fillAfter = true
            rotateAnimation.setInterpolator(LinearInterpolator())

            rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {

                    //(x2,y2)=(x1+l⋅cos(a),y1+l⋅sin(a))
                    sunriseTime.visibility = View.VISIBLE

                    sunriseTime.text = time

                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
            sunriseLineImage.startAnimation(rotateAnimation)
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

    override fun onDayClicked(day: DailyConditions) {

        weatherViewModel.clickedADay(day)

    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "The savedstate is ${weatherViewModel.state}")

        if (!weatherViewModel.state.keys().isEmpty()) {
            Log.i(TAG, "The savedstate is not null")
            setTheView(binding, weatherViewModel.state.get(SAVED_WEATHER)!!, false)
        }
    }


}