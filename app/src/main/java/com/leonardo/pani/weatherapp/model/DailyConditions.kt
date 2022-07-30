package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DailyConditions(val previewWeatherConditions: PreviewConditions, val hoursConditions: MutableList<HourlyConditions>) : Parcelable
