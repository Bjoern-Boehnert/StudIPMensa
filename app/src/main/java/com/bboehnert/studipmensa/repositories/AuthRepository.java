package com.bboehnert.studipmensa.repositories;

import androidx.lifecycle.MutableLiveData;

import com.bboehnert.studipmensa.LoginResult;
import com.bboehnert.studipmensa.SharedPreferencesHelper;
import com.bboehnert.studipmensa.StudipAPI;
import com.bboehnert.studipmensa.models.user.User;
import com.bboehnert.studipmensa.network.BasicAuthInterceptor;
import com.bboehnert.studipmensa.responses.LoginResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthRepository {
    private final BasicAuthInterceptor basic;
    private final SharedPreferencesHelper prefs;
    private StudipAPI api;

    @Inject
    public AuthRepository(StudipAPI api, BasicAuthInterceptor basic, SharedPreferencesHelper prefs) {
        this.api = api;
        this.basic = basic;
        this.prefs = prefs;
    }

    public MutableLiveData<LoginResult> login(String username, String password) {
        basic.setCredentials(username, password);

        final MutableLiveData<LoginResult> mutableLiveData = new MutableLiveData<>();
        api.login().enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                User user = null;
                if (response.isSuccessful()) {
                    user = response.body().getResponse();
                    prefs.setUsername(username);
                    prefs.setPassword(password);
                }

                mutableLiveData.setValue(new LoginResult(user, response.code()));
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                throw new FetchFromAPIException(t.getMessage());
                // Bzw. Fehlermeldung?
            }
        });
        return mutableLiveData;
    }

}
