package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(val currentCondition: CurrentConditions?, val fivedayForecast: FiveDayForecast?,val cityApiLocation: String, val cityName: String): Parcelable {
}