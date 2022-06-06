package com.leonardo.pani.weatherapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.model.Feature
import com.leonardo.pani.weatherapp.model.WeatherForecast
import com.leonardo.pani.weatherapp.repo.RepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: RepoInterface) : ViewModel() {


    private val weatherChannel = Channel<WeatherUseCases>()
    val receiveFromWeatherViewModelChannel = weatherChannel.receiveAsFlow()

    private val _response = ActionLiveData<WeatherForecast>()
    val weatherResponse: LiveData<WeatherForecast>
        get() = _response


    init {

        if (_response.value == null) {
            viewModelScope.launch {
                weatherChannel.send(WeatherUseCases.NavigateToSetLocationScreen())
            }
        }
        //sendWeatherConditionToUI()
    }

    private fun sendWeatherConditionToUI() {

        viewModelScope.launch {
/*

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

                    Get the Current Conditions
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

            }*/
        }


    }

    fun memorizeCity(cityApiLocation: String, cityName: String) {
        Log.d(
            "WeatherViewModel",
            "Received city infos, apilocation: $cityApiLocation and cityname: $cityName"
        )
        viewModelScope.launch {
            //repo.memorizeLocationNameAndLocalApi(cityName, cityApiLocation)
        }
    }



    fun requestWeatherForecastAndCurrentConditions(basicInfo: CityNameAndCoordinates) {
        viewModelScope.launch {
            val weatherInfo =
                repo.getCurrentConditionAndForecasts(cityLatAndLong = basicInfo.coordinates)

            if (weatherInfo.isSuccessful && weatherInfo.body() != null) {

                val forecast = weatherInfo.body()
                Log.i("WeatherViewModel", "weatehr info: $forecast")

                //It's important to copy the result fromm the first api so that we can send all of the info to the _response and hence to the fragment
                _response.sendAction(forecast?.copy(coordinates = basicInfo.coordinates, cityName = basicInfo.cityName)!!)
            } else {
                weatherChannel.send(WeatherUseCases.NavigateToTheErrorPage())
            }

        }
    }

    sealed class WeatherUseCases {

        class NavigateToSetLocationScreen : WeatherUseCases()
        class NavigateToTheErrorPage : WeatherUseCases()

    }


}
