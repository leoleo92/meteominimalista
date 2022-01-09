package com.leonardo.weatherapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

    companion object {
        val CITY = stringPreferencesKey("CITY")
    }

    suspend fun saveLocation(city: String) {

        context.dataStore.edit {
            it[CITY] = city?:""
        }

    }

    fun readLastLocation()= context.dataStore.data.map {
        it[CITY]
    }

}