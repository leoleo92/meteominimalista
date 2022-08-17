package com.leonardo.pani.weatherapp.view.main

import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataStoreInstrumentalTest {


    lateinit var dataStore: DataStoreManager
    private val savedState = SavedStateHandle()
    private lateinit var viewModel: WeatherViewModel
    private val repo = FakeRepo()
    private val cityNameAndCoordinates =
        CityNameAndCoordinates(listOf(45.4654, 9.18592), "Milano") //Coordinates of Milan

    @Before
    fun setUp() {

        dataStore = DataStoreManager(InstrumentationRegistry.getInstrumentation().getTargetContext())
        viewModel = WeatherViewModel(repo, dataStore, savedState)

    }


    @Test
    fun shouldReturnValueIfSavedInDataStore() {

        runBlocking {
            viewModel.memorizeCity(cityNameAndCoordinates)
            dataStore.readLastCityInfo().map {
                assert(it.equals(cityNameAndCoordinates))

            }

        }
    }
}
