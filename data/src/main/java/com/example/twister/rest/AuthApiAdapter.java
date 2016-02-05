package com.example.twister.rest;

import com.example.twister.model.AccessTokenRepresentation;
import com.example.twister.model.EmptyBody;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rx.Observable;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public class AuthApiAdapter implements AuthApi {
    @Override
    public Observable<AccessTokenRepresentation> getAccessToken(String code, String clientId, String clientSecret, EmptyBody body) {
        return getHostAdapter(AuthApi.AUTH_ENDPOINT)
                .create(AuthApi.class)
                .getAccessToken(code, clientId, clientSecret, body);
    }

    private RestAdapter getHostAdapter(String host){
        return new RestAdapter.Builder()
                .setEndpoint(host)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .build();
    }
}