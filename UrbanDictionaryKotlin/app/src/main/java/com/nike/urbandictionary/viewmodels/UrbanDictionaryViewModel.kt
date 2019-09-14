package com.nike.urbandictionary.viewmodels

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.nike.urbandictionary.models.UrbanDictionaryDefinition
import com.nike.urbandictionary.networking.UrbanDictionaryRepository

class UrbanDictionaryViewModel(@get:JvmName("getApplication_") private val application: Application) : AndroidViewModel(application) {
    fun getUrbanDictionaryRepository(term: String): LiveData<List<UrbanDictionaryDefinition>> {
        return UrbanDictionaryRepository.getInstance(application).getUrbanDictionaryResponse(term)
    }
}
