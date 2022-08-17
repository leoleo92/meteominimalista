package com.leonardo.pani.weatherapp.repo

import com.leonardo.pani.weatherapp.model.jsonGenerated.CitiesList
import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import retrofit2.Response

interface RepoInterface {


    suspend fun getCurrentConditionAndForecasts(cityLatAndLong : List<Double>): Response<WeatherForecast>

    suspend fun getCities(cityName: String) :  Response<CitiesList>

    suspend fun getDailyForecasts(cityLatAndLong: List<Double>) : Response<DaysForecasts>

}