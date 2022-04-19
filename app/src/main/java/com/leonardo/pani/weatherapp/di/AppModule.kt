package com.leonardo.pani.weatherapp.di

import com.leonardo.pani.weatherapp.api.WeatherApi
import com.leonardo.pani.weatherapp.utils.Consts
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

/*
    @Provides
    fun getBaseWeatherApiUrl() = Consts.BASE_URL
    */

    @Provides
    @Singleton
    fun provideRetroFitInst(): WeatherApi {
        return Retrofit.Builder().baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}