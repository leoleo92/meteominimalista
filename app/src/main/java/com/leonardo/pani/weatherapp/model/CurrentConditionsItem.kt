package com.leonardo.pani.weatherapp.model

data class CurrentConditionsItem(
    val EpochTime: Int,
    val HasPrecipitation: Boolean,
    val IsDayTime: Boolean,
    val Link: String,
    val LocalObservationDateTime: String,
    val MobileLink: String,
    val PrecipitationType: Any,
    val Temperature: TemperatureX,
    val WeatherIcon: Int,
    val WeatherText: String,
    val locationName: String?
)