package com.nike.urbandictionary.networking

import com.nike.urbandictionary.models.UrbanDictionaryDefinition

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UrbanDictionaryApi {
    @GET("define")
    @Headers("X-RapidAPI-Host: mashape-community-urban-dictionary.p.rapidapi.com", "X-RapidAPI-Key: e7f05b8081msheacca4bbe66cf3dp12acc4jsne0884614299c")
    fun getDictionaryDefinition(@Query("term") term: String): Call<List<UrbanDictionaryDefinition>>
}
