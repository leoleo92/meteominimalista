package com.leonardo.pani.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.pani.weatherapp.model.*
import com.leonardo.pani.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val TAGG = "SearchCityViewModel"


@HiltViewModel
class SearchCityViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {


    private val response:MutableLiveData<List<CitiesItem>>  = MutableLiveData()
    val searchResponse: LiveData<List<CitiesItem>>
        get() = response


    private val cityChannel = Channel<CityChannel>()
    val receiveInfoFromCityChannel = cityChannel.receiveAsFlow()


    fun search(city: String) {
        viewModelScope.launch {
            try {
                val apiResponse = repo.getCities(city)
                if(apiResponse.isSuccessful) {
                    response.value = apiResponse.body()
                } else {
                    cityChannel.send(CityChannel.ReachedLimitRequests())
                }

            }catch(e: Exception) {
                //response.value = emptyList()
                Log.e(TAGG, e.toString())
            }

        }
    }


    fun clickedCity(city: City) {
        viewModelScope.launch {
            cityChannel.send(CityChannel.ClickedCityName(city))
        }
    }


    sealed class CityChannel {
        data class ClickedCityName(val city: City) : CityChannel()
        class ReachedLimitRequests : CityChannel()
    }


}