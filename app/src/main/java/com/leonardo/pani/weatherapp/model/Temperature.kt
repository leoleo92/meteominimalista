package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Temperature(
    val Maximum: Maximum,
    val Minimum: Minimum
) : Parcelable