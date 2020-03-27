package com.litmus.weatherapp.api

import com.litmus.weatherapp.models.WeatherResponse
import com.litmus.weatherapp.models.WeatherResponseObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/forecast")
    fun getWeather(@Query("q") city: String, @Query("appid") apiKey: String): Call<WeatherResponseObject>
}