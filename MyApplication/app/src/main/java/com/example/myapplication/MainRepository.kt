package com.example.myapplication

import com.example.myapplication.models.Country
import com.example.myapplication.network.RetrofitInstance

class MainRepository {

    private val apiService = RetrofitInstance.api

    suspend fun getPosts(): List<Country> {
        return apiService.getData()
    }
}