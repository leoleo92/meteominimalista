package com.leonardo.pani.weatherapp.model.jsonGenerated

data class DailyX(
    val precipitation_hours: List<Double>,
    val precipitation_sum: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>
)