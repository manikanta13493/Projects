package com.nike.urbandictionary.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class UrbanDictionaryRetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(" https://mashape-community-urban-dictionary.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
