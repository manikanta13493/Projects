package com.nike.urbandictionary.networking

import android.app.Application

import androidx.lifecycle.MutableLiveData

import com.nike.urbandictionary.database.UrbanDictionaryDatabase
import com.nike.urbandictionary.models.UrbanDictionaryDefinition

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrbanDictionaryRepository private constructor(application: Application) {

    private val urbanDictionaryApi: UrbanDictionaryApi = UrbanDictionaryRetrofitService.createService(UrbanDictionaryApi::class.java)
    private val urbanDictionaryDatabase: UrbanDictionaryDatabase = UrbanDictionaryDatabase.getInstance(application)

    fun getUrbanDictionaryResponse(term: String): MutableLiveData<List<UrbanDictionaryDefinition>> {
        val urbanDictionaryResponseMutableLiveData = MutableLiveData<List<UrbanDictionaryDefinition>>()
        if (urbanDictionaryDatabase.urbanDictionaryDao().getDefinitions(term).isEmpty()) {
            urbanDictionaryApi.getDictionaryDefinition(term).enqueue(object : Callback<List<UrbanDictionaryDefinition>> {
                override fun onResponse(call: Call<List<UrbanDictionaryDefinition>>,
                                        response: Response<List<UrbanDictionaryDefinition>>) {
                    if (response.isSuccessful) {
                        urbanDictionaryResponseMutableLiveData.value = response.body()
                        urbanDictionaryDatabase.urbanDictionaryDao().insertDefinition(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<List<UrbanDictionaryDefinition>>, t: Throwable) {
                    urbanDictionaryResponseMutableLiveData.value = null
                }
            })
        } else {
            urbanDictionaryResponseMutableLiveData.setValue(urbanDictionaryDatabase.urbanDictionaryDao().getDefinitions(term))
        }
        return urbanDictionaryResponseMutableLiveData
    }

    companion object {
        private var urbanDictionaryRepository: UrbanDictionaryRepository?=null

        fun getInstance(application: Application): UrbanDictionaryRepository {
            if (urbanDictionaryRepository == null) {
                urbanDictionaryRepository = UrbanDictionaryRepository(application)
            }
            return urbanDictionaryRepository!!
        }
    }
}
