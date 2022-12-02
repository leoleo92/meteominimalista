package com.leonardo.pani.weatherapp.repo

import com.leonardo.pani.weatherapp.api.DetailedForecastApi
import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.api.WeatherForecastApi
import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepo @Inject constructor(private val searchPlaceAPI:SearchPlaceApi, private val basicWeatherAPI: WeatherForecastApi, private val detailedWeatherAPI: DetailedForecastApi) : RepoInterface  {


     override suspend fun getCurrentConditionAndForecasts(cityLatAndLong: List<Double>) = basicWeatherAPI.getForecast(latitude = cityLatAndLong.get(1),longitude = cityLatAndLong.get(0))

     override suspend fun getCities(cityName: String) = searchPlaceAPI.getCities(cityName)
     override suspend fun getDailyForecasts(cityLatAndLong: List<Double>): Response<DaysForecasts> = detailedWeatherAPI.getDetailedForecasts(cityLatAndLong.get(1).toString(),cityLatAndLong.get(0).toString())

}