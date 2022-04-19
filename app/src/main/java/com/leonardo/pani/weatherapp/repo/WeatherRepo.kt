package com.leonardo.pani.weatherapp.repo

import com.leonardo.pani.weatherapp.api.WeatherApi
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import javax.inject.Inject


class WeatherRepo @Inject constructor(private val api:WeatherApi, private val data: DataStoreManager) {


    fun getLastCityApiAndName() = data.readLastCityInfo()

    suspend fun memorizeLocationNameAndLocalApi(cityName: String, localApi: String) = data.saveLocalApiAndCityName(cityName,localApi)

    suspend fun getCurrentConditions(cityApiLocation: String) = api.getCurrentCondition(cityApiLocation)

    suspend fun getFiveDayForecast(cityApiLocation: String) = api.fiveDayForecast(cityApiLocation)

    suspend fun getCities(cityName: String) = api.getCitiesList(locationInitials = cityName)

}