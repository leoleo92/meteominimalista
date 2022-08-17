package com.leonardo.pani.weatherapp.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import com.leonardo.pani.weatherapp.view.FakeRepo
import com.leonardo.pani.weatherapp.view.citysearch.MainCoroutineRule
import com.leonardo.pani.weatherapp.view.citysearch.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class WeatherViewModelTest {


    private lateinit var viewModel: WeatherViewModel
    private val repo = FakeRepo()
    private lateinit var dataStore: DataStoreManager
    private val savedState = Mockito.mock(SavedStateHandle::class.java, Mockito.RETURNS_DEEP_STUBS)
    private val cityNameAndCoordinates =
        CityNameAndCoordinates(listOf(45.4654, 9.18592), "Milano") //Coordinates of Milan

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = RuntimeEnvironment.application
        dataStore = DataStoreManager(application)
        viewModel = WeatherViewModel(repo, dataStore, savedState)
    }


    @Test
    fun `should fill the observable data given success responses from the API`() {


        runBlocking {


            viewModel.requestWeatherForecastAndCurrentConditions(cityNameAndCoordinates)


            assert(viewModel.weatherResponse.getOrAwaitValueTest().daily.isNotEmpty())


        }

    }


    @Test
    fun `Received an error from the API, viewModel should send NavigateToErrorPage to the channel`() {

        runTest {
            repo.errorResponse = true

            viewModel.receiveFromWeatherViewModelChannel.test {
                viewModel.requestWeatherForecastAndCurrentConditions(cityNameAndCoordinates)
                assert(expectMostRecentItem() is WeatherViewModel.WeatherUseCases.NavigateToTheErrorPage)
            }
        }

    }

    @Test
    fun `if the user previously searched the weather forecast for a city, provide again the city name, its long and lat`() {

        runBlocking {
            viewModel.memorizeCity(cityNameAndCoordinates)

            dataStore.readLastCityInfo().collect{
                assert(it.equals(cityNameAndCoordinates))

            }

        }


    }

    @Test
    fun `If the user hasn't searched any city yet, call the set location screen channel`() {

        runBlocking {

            viewModel.receiveFromWeatherViewModelChannel.test {
                viewModel.requestWeatherForecastAndCurrentConditions(cityNameAndCoordinates)
                assert(expectMostRecentItem() is WeatherViewModel.WeatherUseCases.NavigateToSetLocationScreen)
            }


        }


    }


}
