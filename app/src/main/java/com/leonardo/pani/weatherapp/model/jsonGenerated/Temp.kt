package com.leonardo.pani.weatherapp.model.jsonGenerated

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
): Parcelable