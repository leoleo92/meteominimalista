package com.leonardo.pani.weatherapp.utils

import com.leonardo.pani.weatherapp.R

object Consts {


    val ICON_IDS_CURRENT_WEATHER_API = mapOf(
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
        "03n" to R.drawable.cloudy,
        "04n" to R.drawable.very_cloudy,
        "09n" to R.drawable.rainy,
        "10n" to R.drawable.rainy_sun,
        "11n" to R.drawable.thunder,
        "13n" to R.drawable.snowy,
        "50n" to R.drawable.fog
    )

    val ICON_IDS_7_DAYS_FORECAST_API = mapOf(
        "0" to R.drawable.sunny_icn,
        "1" to R.drawable.sunny_clouds,
        "2" to R.drawable.sunny_clouds,
        "3" to R.drawable.sunny_clouds,
        "45" to R.drawable.fog,
        "48" to R.drawable.fog,
        "51" to R.drawable.rainy,
        "53" to R.drawable.rainy,
        "55" to R.drawable.rainy,
        "56" to R.drawable.rainy,
        "57" to R.drawable.rainy,
        "61" to R.drawable.rainy,
        "63" to R.drawable.rainy,
        "65" to R.drawable.rainy,
        "66" to R.drawable.rainy,
        "67" to R.drawable.rainy,
        "71" to R.drawable.snowy,
        "73" to R.drawable.snowy,
        "75" to R.drawable.snowy,
        "77" to R.drawable.snowy,
        "80" to R.drawable.rainy,
        "81" to R.drawable.rainy,
        "82" to R.drawable.rainy,
        "85" to R.drawable.snowy,
        "86" to R.drawable.snowy,
        "95" to R.drawable.thunder,
        "96" to R.drawable.thunder,
        "99" to R.drawable.thunder,

        )


    val CODE_TO_ITA_DESCRIPTION = mapOf(
        "0" to "Soleggiato",
        "1" to "In parte soleggiato",
        "2" to "In parte nuvoloso",
        "3" to "Nuvoloso",
        "45" to "Nebbia",
        "46" to "Nebbia",
        "51" to "Pioggerella",
        "53" to "Pioggia moderata",
        "55" to "Pioggia intensa",
        "56" to "Pioggia gelida",
        "57" to "Pioggia gelida intensa",
        "61" to "Pioggia",
        "63" to "Pioggia moderata",
        "65" to "Acquazzone",
        "66" to "Pioggia gelata",
        "67" to "Pioggia gelida intensa",
        "71" to "Neve leggera",
        "73" to "Neve moderata",
        "75" to "Bufera di neve",
        "77" to "Nevischio",
        "80" to "Breve acquazzone",
        "81" to "Acquazzone",
        "82" to "Bombe d'acqua",
        "85" to "Nevicata",
        "86" to "Intensa nevicata",
        "95" to "Temporali e grandine",
        "96" to "Grandinata"



    )
}