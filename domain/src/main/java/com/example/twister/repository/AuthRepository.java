package com.example.twister.repository;

import com.example.twister.model.MetaData;

import rx.Observable;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public interface AuthRepository {
    Observable<MetaData> registerForMetaDataUpdates();
    Observable<com.example.twister.model.AccessToken> registerForAccessTokenUpdates(String code);
}