package com.leonardo.pani.weatherapp.repo

import com.leonardo.pani.weatherapp.model.CitiesList
import com.leonardo.pani.weatherapp.model.WeatherForecast
import retrofit2.Response

interface RepoInterface {


    suspend fun getCurrentConditionAndForecasts(cityLatAndLong : List<Double>): Response<WeatherForecast>

    suspend fun getCities(cityName: String) :  Response<CitiesList>

}