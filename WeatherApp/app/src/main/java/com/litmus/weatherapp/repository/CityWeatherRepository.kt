package com.litmus.weatherapp.repository

import androidx.lifecycle.MutableLiveData
import com.litmus.weatherapp.models.WeatherResponse

interface CityWeatherRepository {

     fun lookupWeather(city: String): MutableLiveData<List<WeatherResponse>>
}