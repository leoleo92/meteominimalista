package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DailyForecast(
    val Date: String,
    val Day: DayX,
    val EpochDate: Int,
    val Link: String,
    val MobileLink: String,
    val Night: Night,
    val Sources: List<String>,
    val Temperature: Temperature,
    val locationName: String?
): Parcelable