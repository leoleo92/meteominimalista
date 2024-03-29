package com.leonardo.pani.weatherapp.view.citysearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.pani.weatherapp.model.jsonGenerated.Feature
import com.leonardo.pani.weatherapp.repo.RepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "SearchCityViewModel"


@HiltViewModel
class SearchCityViewModel @Inject constructor(private val repo: RepoInterface) : ViewModel() {


    private val response: MutableLiveData<List<Feature>> = MutableLiveData()
    val searchResponse: LiveData<List<Feature>>
        get() = response


    private val cityChannel = Channel<CityChannel>()
    val receiveInfoFromCityChannel = cityChannel.receiveAsFlow()


    fun search(city: String) {
        viewModelScope.launch {

            val apiResponse = repo.getCities(city)


            if (apiResponse.isSuccessful) {
                response.value = apiResponse.body()?.features
            } else {
                cityChannel.send(CityChannel.ReachedLimitRequests())
            }
        }
    }


    fun clickedCity(city: Feature) {
        viewModelScope.launch {
            cityChannel.send(CityChannel.ClickedCityName(city))
        }
    }


    sealed class CityChannel {
        data class ClickedCityName(val cityFeature: Feature) : CityChannel()
        class ReachedLimitRequests : CityChannel()
    }


}