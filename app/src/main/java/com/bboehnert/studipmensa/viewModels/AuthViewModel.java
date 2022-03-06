package com.bboehnert.studipmensa.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bboehnert.studipmensa.LoginResult;
import com.bboehnert.studipmensa.repositories.AuthRepository;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;
    private MutableLiveData<LoginResult> mutableLiveData;

    @Inject
    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<LoginResult> login(String username, String password) {
        mutableLiveData = repository.login(username, password);
        return mutableLiveData;
    }
}
