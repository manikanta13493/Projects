package com.nike.urbandictionary.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nike.urbandictionary.models.UrbanDictionaryDefinition;
import com.nike.urbandictionary.networking.UrbanDictionaryRepository;

import java.util.List;

public class UrbanDictionaryViewModel extends AndroidViewModel {

    private Application application;

    public UrbanDictionaryViewModel(Application application){
        super(application);
        this.application = application;
    }
    public LiveData<List<UrbanDictionaryDefinition>> getUrbanDictionaryRepository(String term) {
        return UrbanDictionaryRepository.getInstance(application).getUrbanDictionaryResponse(term);
    }
}
