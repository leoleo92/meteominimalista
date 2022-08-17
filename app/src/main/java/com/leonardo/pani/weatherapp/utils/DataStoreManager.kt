package com.leonardo.pani.weatherapp.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext val context: Context) {


    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)


    companion object {

        val CITY_NAME = stringPreferencesKey("CITY_NAME")
        val CITY_LAT = stringPreferencesKey("CITY_LAT")
        val CITY_LONG = stringPreferencesKey("CITY_LONG")
    }



    suspend fun saveCityNameAndCoordinates( cityNameAndCoordinates : CityNameAndCoordinates) {

        val coordinates = cityNameAndCoordinates.coordinates
        val cityName = cityNameAndCoordinates.cityName

        context.dataStore.edit {
            it[CITY_LAT] = coordinates.get(0).toString()
            it[CITY_LONG] = coordinates.get(1).toString()
            it[CITY_NAME] = cityName
        }

    }

    fun readLastCityInfo() = context.dataStore.data.map {

        val cityLat = it[CITY_LAT]?.toDouble() ?:0.0
        val cityLong = it[CITY_LONG]?.toDouble() ?:0.0
        val cityName = it[CITY_NAME] ?: "N/A"
        CityNameAndCoordinates(listOf(cityLat,cityLong),cityName)
    }


}