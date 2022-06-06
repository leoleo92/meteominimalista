package com.leonardo.pani.weatherapp.model

data class WeatherForecast(
    val cityName: String,
    val coordinates: List<Double>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)