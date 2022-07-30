package com.leonardo.pani.weatherapp.model.jsonGenerated

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
): Parcelable