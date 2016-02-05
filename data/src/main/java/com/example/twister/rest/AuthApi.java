package com.example.twister.rest;

import com.example.twister.model.AccessTokenRepresentation;
import com.example.twister.model.EmptyBody;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface AuthApi {
    public String AUTH_ENDPOINT = "https://api.home.nest.com/oauth2/";

    @POST("/access_token?grant_type=authorization_code")
    @Headers("Content-type: application/x-www-form-urlencoded; charset=UTF-8")
    Observable<AccessTokenRepresentation> getAccessToken(
            @Query("code") String code,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Body EmptyBody body
    );
}