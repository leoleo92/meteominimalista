package com.leonardo.pani.weatherapp.di

import com.google.gson.Gson
import com.leonardo.pani.weatherapp.api.DetailedForecastApi
import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.api.WeatherForecastApi
import com.leonardo.pani.weatherapp.repo.RepoInterface
import com.leonardo.pani.weatherapp.repo.WeatherRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    companion object {
        const val FORECAST_API_KEY = "44898f8f09d19a34de6b729b27c18dd3"
        const val FORECAST_BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val LOCATION_SEARCH_BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/"
        const val MAP_BOX_TOKEN = "pk.eyJ1IjoibGlvbmFwcCIsImEiOiJjbDJqdmJnNTEwajZ4M2ZzM3pzeXAxZXljIn0.6p9_7IB77CwPeb2NxrNdrg"
        const val DAILY_FORECASTS_BASE_URL = "https://api.open-meteo.com/v1/"
    }



    @Provides
    @Singleton
    fun provideRetroFitInst(): SearchPlaceApi {
        return Retrofit.Builder().baseUrl(LOCATION_SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchPlaceApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRetroFitInst2(): WeatherForecastApi {
        return Retrofit.Builder().baseUrl(FORECAST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherForecastApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRettrofitInst3() : DetailedForecastApi {
        return Retrofit.Builder().baseUrl(DAILY_FORECASTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetailedForecastApi::class.java)
    }



    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModules {

        @Binds
        fun provideMainRepositoryImpl(repository: WeatherRepo): RepoInterface

    }




}