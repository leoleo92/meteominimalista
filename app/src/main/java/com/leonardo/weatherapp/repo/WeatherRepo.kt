package com.leonardo.weatherapp.repo

import com.leonardo.weatherapp.api.WeatherApi
import com.leonardo.weatherapp.utils.DataStoreManager
import javax.inject.Inject


class WeatherRepo @Inject constructor(private val api:WeatherApi, private val data: DataStoreManager) {

    suspend fun getWeather() = api.getWeather()

    fun getCity() = data.readLastLocation()

    suspend fun memorizeCity(city: String) = data.saveLocation(city)


}