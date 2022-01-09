package com.leonardo.weatherapp.di

import com.leonardo.weatherapp.api.WeatherApi
import com.leonardo.weatherapp.utils.Consts
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


    @Provides
    fun getBaseWeatherApiUrl() = Consts.BASE_URL

    @Provides
    @Singleton
    fun provideRetroFitInstance(BASE_URL_API: String): WeatherApi =
        Retrofit.Builder().baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

}