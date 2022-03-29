package com.leonardo.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.weatherapp.model.CityItem
import com.leonardo.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val TAGG = "SearchCityViewModel"


@HiltViewModel
class SearchCityViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {


    /*private val response = MutableLiveData<List<CityItem>>()
    val searchResponse: LiveData<List<CityItem>>
        get() = response
*/

    private val cityChannel = Channel<CityChannel>()
    val receiveInfoFromCityChannel = cityChannel.receiveAsFlow()


    fun search(city: String?) {

        viewModelScope.launch {
            try {
                cityChannel.send(CityChannel.SendCityNameList(repo.getCities(city).body()))

            } catch (e: Exception) {
                Log.i(TAGG, e.toString())
            }
        }
    }


    fun clickedCity(city: CityItem) {
        viewModelScope.launch {
            cityChannel.send(CityChannel.ClickedCityName(city))
        }
    }


    sealed class CityChannel {
        data class SendCityNameList(val cityList: List<CityItem>?) : CityChannel()
        data class ClickedCityName(val city: CityItem) : CityChannel()
    }

}