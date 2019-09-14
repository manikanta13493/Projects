package com.nike.urbandictionary.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object UrbanDictionaryRetrofitService {

    private val retrofit = Retrofit.Builder()
            .baseUrl(" https://mashape-community-urban-dictionary.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
