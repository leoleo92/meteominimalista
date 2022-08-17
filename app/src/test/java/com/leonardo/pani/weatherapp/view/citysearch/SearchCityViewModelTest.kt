package com.leonardo.pani.weatherapp.view.citysearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.leonardo.pani.weatherapp.model.jsonGenerated.CitiesList
import com.leonardo.pani.weatherapp.model.jsonGenerated.Feature
import com.leonardo.pani.weatherapp.model.jsonGenerated.Geometry
import com.leonardo.pani.weatherapp.model.jsonGenerated.Properties
import com.leonardo.pani.weatherapp.view.FakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchCityViewModelTest {


    private val repo = Mockito.mock(FakeRepo::class.java, RETURNS_DEEP_STUBS)

    private lateinit var searchCityViewModel: SearchCityViewModel


    @get:Rule
    var mainRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        searchCityViewModel = SearchCityViewModel(repo)

    }

    @Test
    fun `Received a list of 2 Features from the repository,Viewmodel's MutableLiveData should has the size of 2`() {
        runTest {


            val fakeFeatureList = listOf(
                Feature(
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    Geometry(emptyList(), ""),
                    "",
                    "",
                    "",
                    "",
                    "",
                    emptyList(),
                    Properties(""),
                    0.0,
                    "",
                    "",
                    ""
                ),
                Feature(
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    Geometry(emptyList(), ""),
                    "",
                    "",
                    "",
                    "",
                    "",
                    emptyList(),
                    Properties(""),
                    0.0,
                    "",
                    "",
                    ""
                )
            )
            val citiesList = CitiesList("", fakeFeatureList, listOf("Milan"), "")

            Mockito.`when`(repo.getCities("Milan")).thenReturn(Response.success(200, citiesList))

            searchCityViewModel.search("Milan")


            assert(searchCityViewModel.searchResponse.getOrAwaitValueTest().isNotEmpty())

        }

    }

    @Test
    fun `Received an error response from the Api Repostiory, Viewmodel should send a ReachedLimitRequests channel to the view`() {
        runTest {


            Mockito.`when`(repo.getCities("Milan")).thenReturn(Response.error(400, "".toResponseBody()))


            searchCityViewModel.search("Milan")

            searchCityViewModel.receiveInfoFromCityChannel.test {

                assert(expectMostRecentItem() is SearchCityViewModel.CityChannel.ReachedLimitRequests)

            }


        }
    }


}