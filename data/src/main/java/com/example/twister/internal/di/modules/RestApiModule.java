package com.example.twister.internal.di.modules;

import com.example.twister.rest.AuthApi;
import com.example.twister.rest.AuthApiAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestApiModule {

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                //  .registerTypeAdapter(Date.class, createDateSerializer())
                //  .registerTypeAdapter(Date.class, createDateDeserializer())
                .create();
    }

    @Provides
    @Singleton
    AuthApi provideAuthApi() {
        return new AuthApiAdapter();
    }
}