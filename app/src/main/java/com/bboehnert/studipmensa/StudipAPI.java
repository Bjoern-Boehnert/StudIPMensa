package com.bboehnert.studipmensa;

import com.bboehnert.studipmensa.responses.MensaResponse;
import com.bboehnert.studipmensa.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StudipAPI {
    @GET("user")
    Call<LoginResponse> login();

    @GET("mensa/{day_id}")
    Call<MensaResponse> getMensaMenu(@Path(value = "day_id") long day_id);
}
