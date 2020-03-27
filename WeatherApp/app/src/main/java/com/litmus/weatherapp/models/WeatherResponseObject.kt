package com.litmus.weatherapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponseObject(
    val cod: String,
    val message: String,
    @SerializedName("list")
    val weatherResponses: List<WeatherResponse>
) : Parcelable

@Parcelize
data class WeatherResponse(
    @SerializedName("main")
    val mainValue: main,
    @SerializedName("weather")
    val weatherValues: List<weather>
) : Parcelable

@Parcelize
data class main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double
) : Parcelable

@Parcelize
data class weather(
    val main: String,
    val description: String
) : Parcelable

@Parcelize
data class WeatherResponseObjectFailure(
    val throwable: Throwable
) : Parcelable