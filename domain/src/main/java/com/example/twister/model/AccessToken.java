package com.example.twister.model;

public class AccessToken {

    public String token;

    public long expiresIn;

    public AccessToken(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "AccessToken: {token = " + token + ", " + expiresIn + "}";
    }
}