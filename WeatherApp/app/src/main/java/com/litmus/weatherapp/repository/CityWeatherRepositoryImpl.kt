package com.litmus.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.litmus.weatherapp.api.WeatherRetrofit
import com.litmus.weatherapp.api.WeatherService
import com.litmus.weatherapp.models.WeatherResponse
import com.litmus.weatherapp.models.WeatherResponseObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherRepositoryImpl(
    private val weatherService: WeatherService = WeatherRetrofit.getService()
) : CityWeatherRepository {

    override fun lookupWeather(city: String): MutableLiveData<List<WeatherResponse>> {
        val weatherResponseLiveData = MutableLiveData<List<WeatherResponse>>()
        weatherService.getWeather(
            city,
            API_KEY
        ).enqueue(object : Callback<WeatherResponseObject> {
            override fun onResponse(
                call: Call<WeatherResponseObject>,
                response: Response<WeatherResponseObject>
            ) {
                if (response.isSuccessful) {
                    weatherResponseLiveData.value = response.body()?.weatherResponses
                    response.body()?.weatherResponses?.forEach {
                        Log.d("Mani", it.weatherValues.first().main)
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponseObject>, t: Throwable) {
                weatherResponseLiveData.value = null
            }
        })
        return weatherResponseLiveData
    }

    companion object {
        private const val API_KEY = "65d00499677e59496ca2f318eb68c049"
    }
}