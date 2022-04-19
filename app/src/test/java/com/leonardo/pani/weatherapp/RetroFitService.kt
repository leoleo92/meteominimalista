package com.leonardo.pani.weatherapp

import com.leonardo.pani.weatherapp.api.WeatherApi
import com.leonardo.pani.weatherapp.utils.Consts
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitService {


    lateinit var retroFit: WeatherApi

    @Before
    fun setUp() {
        retroFit = Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Test
    fun shouldReturnOkResponseForCurrentCodintionForMilan() {
        runBlocking {

            //214046 LocationApiKey for Milan
            val response = retroFit.getCurrentCondition("214046")

            assertEquals(response.message(),"OK")

        }
    }

    @Test
    fun shouldReturnOkResponseForFiveDayForecastForMilan() {
        runBlocking {

            //214046 LocationApiKey for Milan
            val response = retroFit.fiveDayForecast("214046")
            assertEquals(response.message(),"OK")
            assert(response.body()?.DailyForecasts?.isNotEmpty() as Boolean)

        }
    }

    @Test
    fun shouldReturnOkResponseForCitiesList() {

        runBlocking {
            val response = retroFit.getCitiesList(locationInitials = "Milan")
            assertEquals(response.message(),"OK")
        }

    }

}