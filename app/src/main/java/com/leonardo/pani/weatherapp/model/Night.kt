package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Night(
    val HasPrecipitation: Boolean,
    val Icon: Int,
    val IconPhrase: String,
    val PrecipitationIntensity: String,
    val PrecipitationType: String
) : Parcelable