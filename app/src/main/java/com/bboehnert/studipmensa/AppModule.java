package com.bboehnert.studipmensa;

import android.content.Context;

import com.bboehnert.studipmensa.network.BasicAuthInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public SharedPreferencesHelper provideSharedPreferencesHelper(@ApplicationContext Context context) {
        return new SharedPreferencesHelper(context);
    }

    @Singleton
    @Provides
    public BasicAuthInterceptor provideAuthentication() {
        return new BasicAuthInterceptor();
    }

    @Provides
    public OkHttpClient provideOkHttpClient(BasicAuthInterceptor basic) {
        return new OkHttpClient.Builder()
                .addInterceptor(basic)
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        final String API_BASE_Address = "https://elearning.uni-oldenburg.de/api.php/";
        return new Retrofit.Builder()
                .baseUrl(API_BASE_Address)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public StudipAPI provideStudip(Retrofit retrofit) {
        return retrofit.create(StudipAPI.class);
    }
}