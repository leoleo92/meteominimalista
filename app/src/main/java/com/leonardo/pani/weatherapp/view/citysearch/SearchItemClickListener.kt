package com.leonardo.pani.weatherapp.view.citysearch

import com.leonardo.pani.weatherapp.model.jsonGenerated.Feature

interface SearchItemClickListener {

    fun onItemClicked(city: Feature)

}