package com.leonardo.pani.weatherapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.model.DailyConditions
import com.leonardo.pani.weatherapp.model.HourlyConditions
import com.leonardo.pani.weatherapp.model.PreviewConditions
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import com.leonardo.pani.weatherapp.repo.RepoInterface
import com.leonardo.pani.weatherapp.utils.Consts
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: RepoInterface,
    private val dataStore: DataStoreManager
) : ViewModel() {


    private val weatherChannel = Channel<WeatherUseCases>()
    val receiveFromWeatherViewModelChannel = weatherChannel.receiveAsFlow()

    private val _response = ActionLiveData<WeatherForecast>()
    val weatherResponse: LiveData<WeatherForecast>
        get() = _response


    init {

        viewModelScope.launch {

            dataStore.readLastCityInfo().collect {

                //It means we didn't store any value previosuly and we need to go to the search city fragment
                if (it.coordinates.sum() == 0.0) {

                    weatherChannel.send(WeatherUseCases.NavigateToSetLocationScreen())

                } else {
                    //Get the forecast and send it to the UI
                    requestWeatherForecastAndCurrentConditions(it)

                }
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

    fun requestWeatherForecastAndCurrentConditions(basicInfo: CityNameAndCoordinates) {
        viewModelScope.launch {

            memorizeCity(basicInfo)



            //7 days conditions
            val dailyForecastsResponse = repo.getDailyForecasta(cityLatAndLong = basicInfo.coordinates)
            val dailyForecastsContent = dailyForecastsResponse.body()

            val dailyConditions = mutableListOf<DailyConditions>()


            if (dailyForecastsResponse.isSuccessful && dailyForecastsResponse.body() != null) {


                dailyForecastsContent.run {

                    var hoursStartIndex = 0
                    var hoursEndIndex = 23


                    for (i in 0..6) {

                        //Preview infos
                        val date = this?.daily?.time?.get(i)
                        Log.i("WeatherviewModel", "The weather code is ${this?.daily?.weathercode?.get(i)}")

                        val weather = this?.daily?.weathercode?.get(i)

                        val maxTemp = this?.daily?.temperature_2m_max?.get(i)
                        val minTemp = this?.daily?.temperature_2m_min?.get(i)
                        val sunriseTime = this?.daily?.sunrise?.get(i)?.substring(11, 16)
                        val sunsetTime = this?.daily?.sunrise?.get(i)?.substring(11, 16)

                        val previewWeatherConditions = PreviewConditions(
                            date,
                            weather,
                            maxTemp,
                            minTemp,
                            sunsetTime,
                            sunriseTime
                        )

                        val hoursConditions = mutableListOf<HourlyConditions>()


                        //Detailed
                        for (j in hoursStartIndex..hoursEndIndex) {

                            val temperature = this?.hourly?.temperature_2m?.get(j)
                            val feelsLikeTemp = this?.hourly?.apparent_temperature?.get(j)
                            val weatherCondition = this?.hourly?.weathercode?.get(j)
                            val precipitation = this?.hourly?.precipitation?.get(j)

                            hoursConditions.add(
                                HourlyConditions(
                                    temperature,
                                    feelsLikeTemp,
                                    weatherCondition,
                                    precipitation
                                )
                            )


                        }

                        dailyConditions.add(
                            DailyConditions(
                                previewWeatherConditions,
                                hoursConditions
                            )
                        )
                        hoursStartIndex += 24
                        hoursEndIndex += 24

                        if(hoursEndIndex > 169) {
                            break
                        }
                    }

                    dailyConditions
                }
            }


            //Current conditions
            val weatherInfo =
                repo.getCurrentConditionAndForecasts(cityLatAndLong = basicInfo.coordinates)

            if (weatherInfo.isSuccessful && weatherInfo.body() != null) {

                var forecast = weatherInfo.body()
                Log.i("WeatherViewModel", "weatehr info: $forecast")


                //It's important to copy the result fromm the first api so that we can send all of the info to the _response and hence to the fragment
                _response.sendAction(
                    forecast?.copy(
                        coordinates = basicInfo.coordinates,
                        cityName = basicInfo.cityName,
                        daysForecast = dailyConditions
                    )!!
                )
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
