package com.leonardo.weatherapp.api

import com.leonardo.weatherapp.model.CityItem
import com.leonardo.weatherapp.model.WeatherX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("forecast.json?key=b727a037c74d4c2c8ea11014212710&q=London&days=7")
    suspend fun getWeather(): Response<WeatherX>


    @GET("search.json?key=b727a037c74d4c2c8ea11014212710&q={cityName}")
    suspend fun getCities(@Path("cityName") cityName: String): Response<CityItem>

}