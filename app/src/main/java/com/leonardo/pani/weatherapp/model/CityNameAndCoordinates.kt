package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityNameAndCoordinates(val coordinates: List<Double>, val cityName: String): Parcelable