package com.leonardo.pani.weatherapp.view

import com.leonardo.pani.weatherapp.model.jsonGenerated.CitiesList
import com.leonardo.pani.weatherapp.model.jsonGenerated.DaysForecasts
import com.leonardo.pani.weatherapp.model.jsonGenerated.WeatherForecast
import com.leonardo.pani.weatherapp.repo.RepoInterface
import com.leonardo.pani.weatherapp.view.main.FakeApiPOJOs
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeRepo(): RepoInterface {


    var errorResponse = false

    override suspend fun getCurrentConditionAndForecasts(cityLatAndLong: List<Double>): Response<WeatherForecast> {

        if(!errorResponse) {
            return Response.success(200, FakeApiPOJOs.detailedApiReponse)
        }else return Response.error(400,  "{\"key\":[\"somestuff\"]}".toResponseBody("application/json".toMediaTypeOrNull()))

    }

    override suspend fun getCities(cityName: String): Response<CitiesList> {
        TODO("Not yet implemented")
    }


    override suspend fun getDailyForecasts(cityLatAndLong: List<Double>): Response<DaysForecasts> {

            return Response.success(200, FakeApiPOJOs.previewApiResponse)
    }
}