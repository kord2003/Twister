package com.example.twister.model.mapper;

import com.example.twister.model.AccessToken;
import com.example.twister.model.AccessTokenRepresentation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccessTokenMapper {

    @Inject
    public AccessTokenMapper() {
    }

    public AccessToken transform(AccessTokenRepresentation v) {
        AccessToken accessToken = null;
        if (v != null) {
            accessToken = new AccessToken(v.token, v.expiresIn);
        }
        return accessToken;
    }
}