package com.leonardo.pani.weatherapp.api

import com.leonardo.pani.weatherapp.di.AppModule
import com.leonardo.pani.weatherapp.model.jsonGenerated.CitiesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchPlaceApi {


    @GET("{cityName}.json?proximity=ip&types=place%2Cpostcode%2Caddress&language=it")
    suspend fun getCities(
        @Path("cityName") cityName: String,
        @Query("access_token") token: String = AppModule.MAP_BOX_TOKEN
    ): Response<CitiesList>



}