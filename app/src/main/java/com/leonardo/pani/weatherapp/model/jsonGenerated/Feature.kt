package com.leonardo.pani.weatherapp.model.jsonGenerated

data class Feature(
    val bbox: List<Double>,
    val center: List<Double>,
    val context: List<Context>,
    val geometry: Geometry,
    val id: String,
    val language: String,
    val language_it: String,
    val place_name: String,
    val place_name_it: String,
    val place_type: List<String>,
    val properties: Properties,
    val relevance: Double,
    val cityName: String,
    val cityNameIt: String,
    val type: String
)