package com.nike.urbandictionary.networking;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.nike.urbandictionary.database.UrbanDictionaryDatabase;
import com.nike.urbandictionary.models.UrbanDictionaryDefinition;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrbanDictionaryRepository {

    private static UrbanDictionaryRepository urbanDictionaryRepository;

    private UrbanDictionaryApi urbanDictionaryApi;
    private UrbanDictionaryDatabase urbanDictionaryDatabase;

    public static UrbanDictionaryRepository getInstance(Application application) {
        if (urbanDictionaryRepository == null) {
            urbanDictionaryRepository = new UrbanDictionaryRepository(application);
        }
        return urbanDictionaryRepository;
    }

    private UrbanDictionaryRepository(Application application) {
        urbanDictionaryApi = UrbanDictionaryRetrofitService.createService(UrbanDictionaryApi.class);
        urbanDictionaryDatabase = UrbanDictionaryDatabase.getInstance(application);
    }

    public MutableLiveData<List<UrbanDictionaryDefinition>> getUrbanDictionaryResponse(String term) {
        final MutableLiveData<List<UrbanDictionaryDefinition>> urbanDictionaryResponseMutableLiveData = new MutableLiveData<>();
        if (urbanDictionaryDatabase.urbanDictionaryDao().getDefinitions(term).isEmpty()) {
            urbanDictionaryApi.getDictionaryDefinition(term).enqueue(new Callback<List<UrbanDictionaryDefinition>>() {
                @Override
                public void onResponse(Call<List<UrbanDictionaryDefinition>> call,
                                       Response<List<UrbanDictionaryDefinition>> response) {
                    if (response.isSuccessful()) {
                        urbanDictionaryResponseMutableLiveData.setValue(response.body());
                        urbanDictionaryDatabase.urbanDictionaryDao().insertDefinition(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<UrbanDictionaryDefinition>> call, Throwable t) {
                    urbanDictionaryResponseMutableLiveData.setValue(null);
                }
            });
        } else {
            urbanDictionaryResponseMutableLiveData.setValue(urbanDictionaryDatabase.urbanDictionaryDao().getDefinitions(term));
        }
        return urbanDictionaryResponseMutableLiveData;
    }
}
