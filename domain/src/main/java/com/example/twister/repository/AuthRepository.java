package com.example.twister.repository;

import rx.Observable;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public interface AuthRepository {
    Observable<com.example.twister.model.ClientMetaData> registerForClientMetaDataUpdates();
    Observable<com.example.twister.model.AccessToken> registerForAccessTokenUpdates(String code);
}