package com.leonardo.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.weatherapp.model.Forecast
import com.leonardo.weatherapp.model.Forecastday
import com.leonardo.weatherapp.model.Weather
import com.leonardo.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TAG = "WeatherViewModel"

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {


    val weatherChannel = Channel<WeatherChannel>()

    //Get the data to pass to mainactivity. We need livedata
    private val _response = MutableLiveData<List<Forecastday>?>()
    val weatherResponse: LiveData<List<Forecastday>?>
        get() = _response



    init {
        viewModelScope.launch {
            _response.postValue(repo.getWeather().body()?.forecast?.forecastday)
        }
    }


    suspend fun setCity(city: String) {
        repo.memorizeCity(city)
    }

    fun getCity() = repo.getCity()


    sealed class WeatherChannel {
        class NavigateToSetLocationScreen: WeatherChannel()
    }


}