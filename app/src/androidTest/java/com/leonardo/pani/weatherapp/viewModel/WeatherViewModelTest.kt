package com.leonardo.pani.weatherapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.leonardo.pani.weatherapp.api.WeatherApi
import com.leonardo.pani.weatherapp.repo.WeatherRepo
import com.leonardo.pani.weatherapp.utils.DataStoreManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class WeatherViewModelTest {


    lateinit var viewmodel: WeatherViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    val weatherApi = mock(WeatherApi::class.java)
    val dataStoreManager = DataStoreManager(ApplicationProvider.getApplicationContext())
    val repo = WeatherRepo(weatherApi, dataStoreManager)


    @Before
    fun setUp() {

        viewmodel = WeatherViewModel(repo)

    }

    @Test
    fun shouldGetAPairOfApiLocationAndCityName() {

        runBlocking {
            repo.memorizeLocationNameAndLocalApi("Milan", "214046")
            val result = viewmodel.weatherResponse.getOrAwaitValue()
            assertEquals(result.cityName, "Milan")
            assertEquals(result.cityApiLocation, "214046")
        }

    }


    @Test
    fun getReceiveFromWeatherViewModelChannel() {
    }

    @Test
    fun getWeatherResponse() {
    }

    @Test
    fun collectSavedCityApiLocationAndCityName() {
    }

    @Test
    fun goToSetLocationScreen() {
    }

    @Test
    fun getInfoFromCitySearch() {
    }
}