package com.leonardo.pani.weatherapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.model.DailyConditions
import com.leonardo.pani.weatherapp.model.HourlyConditions
import com.leonardo.pani.weatherapp.model.PreviewConditions
import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import com.leonardo.pani.weatherapp.repo.RepoInterface
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

const val SAVED_WEATHER = "saved_weather_forecast"


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: RepoInterface,
    private val dataStore: DataStoreManager,
    val state: SavedStateHandle
) : ViewModel() {


    private val weatherChannel = Channel<WeatherUseCases>()
    val receiveFromWeatherViewModelChannel = weatherChannel.receiveAsFlow()

    private val _response = ActionLiveData<WeatherForecast>()
    val weatherResponse: LiveData<WeatherForecast>
        get() = _response


    init {


        viewModelScope.launch {


            val cityNameAndCoordinates = dataStore.readLastCityInfo().first()


            if (cityNameAndCoordinates.coordinates.sum() == 0.0) {

                weatherChannel.send(WeatherUseCases.NavigateToSetLocationScreen())

            } else {
                //Get the forecast and send it to the UI
                requestWeatherForecastAndCurrentConditions(cityNameAndCoordinates)


            }
        }
    }


    suspend fun memorizeCity(cityNameAndCoordinates: CityNameAndCoordinates) {
        dataStore.saveCityNameAndCoordinates(cityNameAndCoordinates)

    }

    fun goToSearchCityFragment() {
        viewModelScope.launch {
            weatherChannel.send(WeatherUseCases.NavigateToSetLocationScreen())
        }
    }

    suspend fun requestWeatherForecastAndCurrentConditions(basicInfo: CityNameAndCoordinates) {


        memorizeCity(basicInfo)


        //7 days conditions
        //var dailyForecastsResponse: Response<DaysForecasts>? = null
        val dailyForecastsResponse = repo.getDailyForecasts(cityLatAndLong = basicInfo.coordinates)

        //Current conditions
        //var weatherInfo : Response<WeatherForecast>? = null
        val weatherInfo = repo.getCurrentConditionAndForecasts(cityLatAndLong = basicInfo.coordinates)


        val dailyForecastsContent = dailyForecastsResponse?.body()

        val dailyConditions = mutableListOf<DailyConditions>()


        Log.i("WeatherViewModel","Called the view model!")

        //if ((dailyForecastsResponse.isSuccessful && dailyForecastsResponse?.body() != null) && (weatherInfo.isSuccessful && weatherInfo.body() != null))
        if ((dailyForecastsResponse.isSuccessful && dailyForecastsResponse?.body() != null) && (weatherInfo.isSuccessful && weatherInfo.body() != null)) {


            dailyForecastsContent.run {


                var hoursStartIndex = 0
                var hoursEndIndex = 23

                val numberOfForecastDays = this?.daily?.weathercode!!

                //For every day create an object with the preview and detailed information
                for (i in 0..numberOfForecastDays.size) {

                    //Preview infos
                    val date = this.daily.time.get(i)
                    val weather = this.daily.weathercode.get(i)
                    val maxTemp = this.daily.temperature_2m_max.get(i)
                    val minTemp = this.daily.temperature_2m_min.get(i)
                    val sunriseTime = this.daily.sunrise.get(i).substring(11, 16)
                    val sunsetTime = this.daily.sunset.get(i).substring(11, 16)

                    val previewWeatherConditions = PreviewConditions(
                        date,
                        weather,
                        maxTemp,
                        minTemp,
                        sunsetTime,
                        sunriseTime
                    )

                    val hoursConditions = mutableListOf<HourlyConditions>()


                    //Detailed info
                    for (j in hoursStartIndex..hoursEndIndex) {

                        val detailedForecastListSize =this.hourly.weathercode.size

                        if(j < detailedForecastListSize) {
                        val hour = this.hourly.time.get(j).substring(11, 16)
                        val temperature = this.hourly.temperature_2m.get(j)
                        val feelsLikeTemp = this.hourly.apparent_temperature.get(j)
                        val weatherCondition = this.hourly.weathercode.get(j)
                        val precipitation = this.hourly.precipitation.get(j)



                        hoursConditions.add(
                            HourlyConditions(
                                hour,
                                temperature,
                                feelsLikeTemp,
                                weatherCondition,
                                precipitation
                            )
                        )
                    }

                    }

                    dailyConditions.add(
                        DailyConditions(
                            previewWeatherConditions,
                            hoursConditions
                        )
                    )
                    hoursStartIndex += 24
                    hoursEndIndex += 24

                    if (hoursEndIndex >= 168) {
                        break
                    }
                }

            }

            //Current weather conditions
            var forecast = weatherInfo?.body()

            val finalWeatherForecast = forecast?.copy(
                coordinates = basicInfo.coordinates,
                cityName = basicInfo.cityName,
                daysForecast = dailyConditions
            )

            state.set(SAVED_WEATHER, finalWeatherForecast)

            //It's important to copy the result from the first api so that we can send all of the info to the _response and hence to the fragment
            _response.value = finalWeatherForecast!!


        } else {
            weatherChannel.send(WeatherUseCases.NavigateToTheErrorPage())
        }



    }

    fun sendNameAndGetForecasts(basicInfo: CityNameAndCoordinates) = viewModelScope.launch {
        requestWeatherForecastAndCurrentConditions(basicInfo)
    }


    fun clickedADay(day: DailyConditions) {

        viewModelScope.launch {
            weatherChannel.send(WeatherUseCases.NavigateToDetailWeatherScreen(day))
        }
    }

    sealed class WeatherUseCases {

        class NavigateToSetLocationScreen : WeatherUseCases()
        class NavigateToDetailWeatherScreen(val day: DailyConditions) : WeatherUseCases()
        class NavigateToTheErrorPage : WeatherUseCases()

    }


}
