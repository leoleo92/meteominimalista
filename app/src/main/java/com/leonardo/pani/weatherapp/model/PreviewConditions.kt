package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PreviewConditions(val date: String?,val  weather: Int?,val maxTemp: Double?,val minTemp: Double?,val sunsetTime: String?,val sunriseTime: String?) : Parcelable
