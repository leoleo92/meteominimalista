package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FiveDayForecast(
    val DailyForecasts: List<DailyForecast>,
    val Headline: Headline
) : Parcelable