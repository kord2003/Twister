package com.example.twister.repository;

import com.example.twister.model.AccessToken;
import com.example.twister.model.Device;
import com.example.twister.model.Structure;

import java.util.List;

import rx.Observable;

public interface NestRepository {
    Observable<List<Structure>> registerForStructuresUpdates(AccessToken accessToken);

    Observable<List<Device>> registerForDevicesUpdates();
}