package com.leonardo.pani.weatherapp.model.jsonGenerated

data class HourlyX(
    val apparent_temperature: List<Double>,
    val precipitation: List<Double>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>
)