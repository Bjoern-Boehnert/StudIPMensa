package com.bboehnert.studipmensa.network;

import java.io.IOException;

import javax.inject.Singleton;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    public void setCredentials(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (credentials == null) {
            throw new RuntimeException("setCredetials muss im Repository gesetzt sein");
        } else {
            requestBuilder.addHeader("Authorization", credentials);
        }
        return chain.proceed(requestBuilder.build());
    }
}