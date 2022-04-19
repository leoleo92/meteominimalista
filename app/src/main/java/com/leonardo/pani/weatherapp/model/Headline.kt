package com.leonardo.pani.weatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Headline(
    val Category: String,
    val EffectiveDate: String,
    val EffectiveEpochDate: Int,
    val EndDate: String,
    val EndEpochDate: Int,
    val Link: String,
    val MobileLink: String,
    val Severity: Int,
    val Text: String
):Parcelable