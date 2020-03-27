package com.litmus.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.litmus.weatherapp.models.WeatherResponse
import com.litmus.weatherapp.repository.CityWeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CityWeatherLookupViewModel(private val cityWeatherRepository: CityWeatherRepository) :
    ViewModel() {

    var city = MutableLiveData<String>()

    fun setCity(cityValue: String) {
        city.value = cityValue
    }

    fun lookUpCityWeather(): LiveData<List<WeatherResponse>> {
        return cityWeatherRepository.lookupWeather(city.value.orEmpty())
    }
}