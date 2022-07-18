package com.leonardo.pani.weatherapp.api

import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailedForecastApi {

    @GET("forecast")
    suspend fun getDetailedForecasts(@Query("latitude")lat:String,@Query("longitude")lgt:String,
    @Query("hourly",encoded = true) hourly: String = "temperature_2m,apparent_temperature,precipitation,weathercode",
    @Query("daily",encoded = true) daily:String = "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,precipitation_hours",
    @Query("timezone",encoded = true) timeZone: String = "Europe%2FRome") : Response<DaysForecasts>

}