package com.leonardo.pani.weatherapp.model.jsonGenerated

data class DaysForecasts(
    val daily: DailyX,
    val daily_units: DailyUnits,
    val elevation: Double,
    val generationtime_ms: Double,
    val hourly: HourlyX,
    val hourly_units: HourlyUnits,
    val latitude: Double,
    val longitude: Double,
    val utc_offset_seconds: Int
)