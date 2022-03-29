package com.leonardo.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.weatherapp.model.Forecastday
import com.leonardo.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TAG = "WeatherViewModel"

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {


    private val weatherChannel = Channel<WeatherChannel>()
    val receiveFromWeatherViewModelChannel = weatherChannel.receiveAsFlow()

    //Get the data to pass to mainactivity. We need livedata
    private val _response = MutableLiveData<List<Forecastday>?>()
    val weatherResponse: LiveData<List<Forecastday>?>
        get() = _response


    init {
        viewModelScope.launch {
            val city = getCity()
            _response.postValue(repo.getWeather(city.toString(), "7").body()?.forecast?.forecastday)
        }
    }


    suspend fun setCity(city: String) {

        repo.memorizeCity(city)

    }

    fun getCity() = repo.getlastLocation()


    suspend fun getCity(city: String) = repo.getCities(city)

    fun goToSetLocationScreen() {
        viewModelScope.launch {
            weatherChannel.send(WeatherChannel.NavigateToSetLocationScreen())
        }
    }

    fun getInfoFromNewCitySearch(cityName: String) {
        viewModelScope.launch {
            setCity(cityName)
            weatherChannel.send(WeatherChannel.SetNewCity(cityName))
        }
    }


    sealed class WeatherChannel {
        class NavigateToSetLocationScreen : WeatherChannel()
        data class SetNewCity(val city: String) : WeatherChannel()
    }


}