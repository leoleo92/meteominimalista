package com.leonardo.pani.weatherapp.model.jsonGenerated

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Parcelable