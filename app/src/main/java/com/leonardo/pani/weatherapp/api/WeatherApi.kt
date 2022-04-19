package com.leonardo.pani.weatherapp.api

import com.leonardo.pani.weatherapp.BuildConfig
import com.leonardo.pani.weatherapp.model.Cities
import com.leonardo.pani.weatherapp.model.CurrentConditions
import com.leonardo.pani.weatherapp.model.FiveDayForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {

    @GET("forecasts/v1/daily/5day/{apiKeyLocation}")
    suspend fun fiveDayForecast(@Path("apiKeyLocation") locationKey: String, @Query("apikey") api_key: String = BuildConfig.MAIN_API, @Query("language") lang: String = "it-it"): Response<FiveDayForecast>

    @GET("currentconditions/v1/{apiKeyLocation}")
    suspend fun getCurrentCondition(@Path("apiKeyLocation") locationKey: String, @Query("apikey") api_key: String = BuildConfig.MAIN_API, @Query("language") lang : String = "it-it"): Response<CurrentConditions>

    @GET("locations/v1/cities/autocomplete?")
    suspend fun getCitiesList(@Query("apikey") api_key: String = BuildConfig.MAIN_API, @Query("q") locationInitials : String ,@Query("language") lang : String = "it-it") : Response<Cities>

}