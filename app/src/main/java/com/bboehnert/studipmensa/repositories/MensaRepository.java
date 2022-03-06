package com.bboehnert.studipmensa.repositories;

import androidx.lifecycle.MutableLiveData;

import com.bboehnert.studipmensa.responses.MensaResponse;
import com.bboehnert.studipmensa.StudipAPI;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MensaRepository {
    private final StudipAPI api;

    @Inject
    public MensaRepository(StudipAPI api) {
        this.api = api;
    }

    public MutableLiveData<MensaResponse> getMensaOfDay(long dayId) {
        final MutableLiveData<MensaResponse> mutableLiveData = new MutableLiveData<>();

        this.api.getMensaMenu(dayId).enqueue(new Callback<MensaResponse>() {
            @Override
            public void onResponse(Call<MensaResponse> call, Response<MensaResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
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
        return mutableLiveData;
    }
}
