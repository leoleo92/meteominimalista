package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Minimum(
    val Unit: String,
    val UnitType: Int,
    val Value: Double
) : Parcelable