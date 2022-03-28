package com.bboehnert.studipmensa.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bboehnert.studipmensa.StudipAPI;
import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.responses.MensaResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MensaRepository {
    private final StudipAPI api;
    private final MutableLiveData<List<FoodGroupDisplayable>> mutableLiveData;
    private final List<FoodGroupDisplayable> list;

    @Inject
    public MensaRepository(StudipAPI api) {
        this.api = api;
        this.list = new ArrayList<>();
        mutableLiveData = new MutableLiveData<>();
    }

    public void setMensaOfDay(long dayId) {
        this.api.getMensaMenu(dayId).enqueue(new Callback<MensaResponse>() {
            @Override
            public void onResponse(Call<MensaResponse> call, Response<MensaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list.clear();

                    if (response.body().getResponse().getWechloy() != null) {
                        list.add(response.body().getResponse().getWechloy());
                    }

                    if (response.body().getResponse().getUhlhornweg() != null) {
                        list.add(response.body().getResponse().getUhlhornweg());
                    }

                    mutableLiveData.setValue(list);
                } else {
                    mutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MensaResponse> call, Throwable t) {
                // Keinen Mensaplan bzw. Parsing Error
                mutableLiveData.setValue(null);
            }
        });
    }

    public LiveData<List<FoodGroupDisplayable>> getMensaOfDay() {
        return mutableLiveData;
    }
}
