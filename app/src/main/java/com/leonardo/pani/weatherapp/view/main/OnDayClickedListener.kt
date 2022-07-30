package com.leonardo.pani.weatherapp.view.main

import com.leonardo.pani.weatherapp.model.DailyConditions

interface OnDayClickedListener {

    fun onDayClicked(day: DailyConditions)

}