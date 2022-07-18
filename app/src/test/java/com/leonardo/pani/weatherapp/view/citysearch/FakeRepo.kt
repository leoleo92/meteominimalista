package com.leonardo.pani.weatherapp.view.citysearch

import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.model.jsonGenerated.CitiesList
import com.leonardo.pani.weatherapp.repo.RepoInterface
import retrofit2.Response

class FakeRepo(val api: SearchPlaceApi): RepoInterface {



    override suspend fun getCities(cityName: String): Response<CitiesList> {

        return api.getCities(cityName)

    }
}