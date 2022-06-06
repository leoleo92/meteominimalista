package com.leonardo.pani.weatherapp.utils

import com.leonardo.pani.weatherapp.R

object Consts {

    const val BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/"
    const val MAP_BOX_TOKEN = "pk.eyJ1IjoibGlvbmFwcCIsImEiOiJjbDJqdmJnNTEwajZ4M2ZzM3pzeXAxZXljIn0.6p9_7IB77CwPeb2NxrNdrg"

    val ICON_IDS = mapOf(
        "01d" to R.drawable.sunny_icn,
        "02d" to R.drawable.sunny_clouds,
        "03d" to R.drawable.cloudy,
        "04d" to R.drawable.very_cloudy,
        "09d" to R.drawable.rainy,
        "10d" to R.drawable.rainy_sun,
        "11d" to R.drawable.thunder,
        "13d" to R.drawable.snowy,
        "50d" to R.drawable.fog,
        "01n" to R.drawable.moon,
        "02n" to R.drawable.moon_cloud,
        "03" to R.drawable.cloudy,
        "04n" to R.drawable.very_cloudy,
        "09n" to R.drawable.rainy,
        "10n" to R.drawable.rainy_sun,
        "11n" to R.drawable.thunder,
        "13n" to R.drawable.snowy,
        "50n" to R.drawable.fog
    )


}