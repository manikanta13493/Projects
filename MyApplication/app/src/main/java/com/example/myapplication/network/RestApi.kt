package com.example.myapplication.network

import com.example.myapplication.models.Country
import com.example.myapplication.models.Name
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("v3.1/subregion/{country}")
    suspend fun getData(@Path("country") country: String = "Australia"): List<Country>
}