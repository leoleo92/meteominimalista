package com.leonardo.pani.weatherapp.view.main

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.leonardo.pani.weatherapp.model.City
import com.leonardo.pani.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {


    private val weatherChannel = Channel<WeatherUseCases>()
    val receiveFromWeatherViewModelChannel = weatherChannel.receiveAsFlow()

    private val _response = ActionLiveData<City>()
    val weatherResponse: LiveData<City>
        get() = _response


    init {

        sendWeatherConditionToUI()
    }

    private fun sendWeatherConditionToUI() {

        viewModelScope.launch {


            collectSavedCityApiLocationAndCityName().collect { ApiAndCityName ->

                val apiLocation = ApiAndCityName.first
                val cityName = ApiAndCityName.second

                var city = City(
                    currentCondition = null,
                    fivedayForecast = null,
                    cityApiLocation = apiLocation,
                    cityName = cityName
                )


                if (apiLocation.isNotEmpty() && cityName.isNotEmpty()) {

                    Log.d("WeatherViewModel", "The apilocation is: $apiLocation")

                    //Get the Current Conditions
                    val currentConditionData =
                        repo.getCurrentConditions(apiLocation)
                    val currentCondition = currentConditionData.body()

                    //Get the Five day forecast
                    val fiveDayForecastData =
                        repo.getFiveDayForecast(apiLocation)
                    val fiveDayForecastList = fiveDayForecastData.body()

                    //Response for fivedayforecast: Unauthorized
                    Log.d("WeatherViewModel", "Response for fivedayforecast: ${fiveDayForecastData.raw()}")
                    if(fiveDayForecastData.code() != 200 && currentConditionData.code() != 200) {
                        weatherChannel.send(WeatherUseCases.NavigateToTheErrorPage())
                    }

                    city = City(currentCondition, fiveDayForecastList, apiLocation, cityName!!)


                }
                _response.postValue(city)

            }
        }


    }

    fun memorizeCity(cityApiLocation: String, cityName: String) {
        Log.d("WeatherViewModel", "Received city infos, apilocation: $cityApiLocation and cityname: $cityName")
        viewModelScope.launch {
            repo.memorizeLocationNameAndLocalApi(cityName, cityApiLocation)
        }
    }

    fun collectSavedCityApiLocationAndCityName() = repo.getLastCityApiAndName()

    fun goToSetLocationScreen() {
        viewModelScope.launch {
            weatherChannel.send(WeatherUseCases.NavigateToSetLocationScreen())
        }
    }

    fun checkTheReceivedCity(city: City?) {

        if (city != null) {

            //The city is not null, we've got a successful response to the API request

            Log.i(
                "WeatherViewModel",
                "The user searched the weather forecasts for ${city.cityName}"
            )

            // Handle the textValue result
            memorizeCity(city.cityApiLocation, city.cityName)
        } else {

            //The city is null, which means we've reached the daily limit requests for the API
            viewModelScope.launch {
                weatherChannel.send(WeatherUseCases.NavigateToTheErrorPage())
            }
        }

    }

    sealed class WeatherUseCases {

        class NavigateToSetLocationScreen : WeatherUseCases()
        class NavigateToTheErrorPage : WeatherUseCases()

    }




}
