package com.leonardo.pani.weatherapp.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext val context: Context) {


    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)


    companion object {
        val CITY_API = stringPreferencesKey("CITY_API")
        val CITY_NAME = stringPreferencesKey("CITY_NAME")
    }



    suspend fun saveLocalApiAndCityName(cityName: String, localApi: String) {

        context.dataStore.edit {
            it[CITY_NAME] = cityName
            it[CITY_API] = localApi
        }

    }

    fun readLastCityInfo() = context.dataStore.data.map {

        val cityApi = it[CITY_API] ?:""
        val cityName = it[CITY_NAME] ?:""
        Pair(cityApi,cityName)
    }


}