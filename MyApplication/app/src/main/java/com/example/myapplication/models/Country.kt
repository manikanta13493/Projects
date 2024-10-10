package com.example.myapplication.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class Country(
    @SerializedName("name")
    val name:Name,
    @SerializedName("currencies")
    val currencies: JsonObject,
    @SerializedName("capital")
    val capital: List<String>,
    )
