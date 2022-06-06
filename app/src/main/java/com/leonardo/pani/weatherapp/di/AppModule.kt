package com.leonardo.pani.weatherapp.di

import com.leonardo.pani.weatherapp.api.SearchPlaceApi
import com.leonardo.pani.weatherapp.api.WeatherForecastApi
import com.leonardo.pani.weatherapp.repo.RepoInterface
import com.leonardo.pani.weatherapp.repo.WeatherRepo
import com.leonardo.pani.weatherapp.utils.Consts
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    companion object {
        const val FORECAST_API_KEY = "44898f8f09d19a34de6b729b27c18dd3"
        const val FORECAST_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }



    @Provides
    @Singleton
    fun provideRetroFitInst(): SearchPlaceApi {
        return Retrofit.Builder().baseUrl(Consts.BASE_URL)
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


    @Module
    @InstallIn(SingletonComponent::class) // or whatever graph fits your need the best
    interface RepositoryModules {
        @Binds
        fun provideMainRepositoryImpl(repository: WeatherRepo): RepoInterface
    }
    /*@Binds
    abstract fun bindingFunction(myImplementor: WeatherRepo) : RepoInterface




/*
    @Singleton
     fun bindingFunction(myImplementor: WeatherRepo) : RepoInterface*/


    object MyModule{


    }*/


}