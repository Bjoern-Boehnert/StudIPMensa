package com.bboehnert.studipmensa.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bboehnert.studipmensa.responses.MensaResponse;
import com.bboehnert.studipmensa.repositories.MensaRepository;

import javax.inject.Inject;

public class MensaViewModel extends ViewModel {

    private MensaRepository repository;
    private MutableLiveData<MensaResponse> mutableLiveData;

    @Inject
    public MensaViewModel(MensaRepository repository) {
        this.repository = repository;
    }

    public LiveData<MensaResponse> getMensaMenu(long day_Id) {
        mutableLiveData = repository.getMensaOfDay(day_Id);
        return mutableLiveData;
    }

}
