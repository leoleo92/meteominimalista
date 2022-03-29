package com.leonardo.weatherapp.api

import com.leonardo.weatherapp.model.CityItem
import com.leonardo.weatherapp.model.WeatherX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json?key=b727a037c74d4c2c8ea11014212710")
    suspend fun getWeather(@Query("q") cityName: String,@Query("days")days: String): Response<WeatherX>


    @GET("search.json?key=b727a037c74d4c2c8ea11014212710")
    suspend fun getCities(@Query("q") cityName: String?): Response<List<CityItem>>


}