package com.leonardo.pani.weatherapp.model

data class CitiesList(
    val attribution: String,
    val features: List<Feature>,
    val query: List<String>,
    val type: String
)