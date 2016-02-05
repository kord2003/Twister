package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

public class AccessTokenRepresentation {

    @SerializedName("access_token")
    public String token;

    @SerializedName("expires_in")
    public long expiresIn;

    public AccessTokenRepresentation() {

    }

    public AccessTokenRepresentation(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}