package com.leonardo.pani.weatherapp.model.jsonGenerated

import com.leonardo.pani.weatherapp.model.DailyConditions

data class WeatherForecast(
    var cityName: String,
    var coordinates: List<Double>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    var daysForecast: List<DailyConditions>?
)