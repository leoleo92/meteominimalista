package com.leonardo.pani.weatherapp.repo

import com.leonardo.pani.weatherapp.api.DetailedForecastApi
import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.api.WeatherForecastApi
import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepo @Inject constructor(private val api:SearchPlaceApi, private val api2: WeatherForecastApi, private val api3: DetailedForecastApi,private val data: DataStoreManager) : RepoInterface  {



     //fun getLastCityApiAndName() = data.readLastCityInfo()

     override suspend fun getCurrentConditionAndForecasts(cityLatAndLong: List<Double>) = api2.getForecast(latitude = cityLatAndLong.get(0),longitude = cityLatAndLong.get(1))

     override suspend fun getCities(cityName: String) = api.getCities(cityName)
     override suspend fun getDailyForecasta(cityLatAndLong: List<Double>): Response<DaysForecasts> = api3.getDetailedForecasts(cityLatAndLong.get(0).toString(),cityLatAndLong.get(1).toString())

}