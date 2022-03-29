package com.leonardo.weatherapp.repo

import com.leonardo.weatherapp.api.WeatherApi
import com.leonardo.weatherapp.utils.DataStoreManager
import javax.inject.Inject


class WeatherRepo @Inject constructor(private val api:WeatherApi, private val data: DataStoreManager) {

    suspend fun getWeather(city: String,days: String) = api.getWeather(city,days)

    fun getlastLocation() = data.readLastLocation()

    suspend fun getCity(name: String) = api

    suspend fun getCities(name: String?) = api.getCities(name)

    suspend fun memorizeCity(city: String) = data.saveLocation(city)


}