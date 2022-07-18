package com.leonardo.pani.weatherapp.api

import com.leonardo.pani.weatherapp.di.AppModule
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {



    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
        @Query("lang") language: String = "it",
        @Query("appid") apiKey: String = AppModule.FORECAST_API_KEY,
        @Query("units") unit: String = "metric"
    ): Response<WeatherForecast>



}