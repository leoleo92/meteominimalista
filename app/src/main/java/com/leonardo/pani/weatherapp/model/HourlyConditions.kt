package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HourlyConditions(val hour: String?,val temperature: Double?, val feelsLikeTemp: Double?, val weatherCondition: Int?,val precipitation: Double?) : Parcelable
