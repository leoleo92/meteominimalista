package com.leonardo.pani.weatherapp

import com.leonardo.pani.weatherapp.api.DetailedForecastApi
import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.api.WeatherForecastApi
import com.leonardo.pani.weatherapp.di.AppModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitService {


    lateinit var searchPlaceApi: SearchPlaceApi
    lateinit var weatherConditionApi: WeatherForecastApi
    lateinit var detailedForecastApi: DetailedForecastApi


    @Test
    fun `Shoulde return ok response from the detailedForecast Api in order to get 7 days forecast`() {
        runBlocking {


            detailedForecastApi = Retrofit.Builder()
                .baseUrl(AppModule.DAILY_FORECASTS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DetailedForecastApi::class.java)


            val response = detailedForecastApi.getDetailedForecasts("45.4654","9.18592")
            assertEquals(response.isSuccessful,true)


        }
    }

    @Test
    fun `should get current weather conditions for the city Milan`() {
        runBlocking {

            weatherConditionApi = Retrofit.Builder()
                .baseUrl(AppModule.FORECAST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherForecastApi::class.java)


            val response = weatherConditionApi.getForecast(	45.4654,	9.18592)
            assertEquals(response.isSuccessful,true)

        }
    }

    @Test
    fun `should return ok Response For CitiesList`() {



        searchPlaceApi = Retrofit.Builder()
            .baseUrl(AppModule.LOCATION_SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchPlaceApi::class.java)

        runBlocking {
            val response = searchPlaceApi.getCities(cityName = "Milano")
            assertEquals(response.isSuccessful,true)
        }

    }

}