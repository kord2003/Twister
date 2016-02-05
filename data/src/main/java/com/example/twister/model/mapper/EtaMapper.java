package com.example.twister.model.mapper;

import com.example.twister.model.EtaRepresentation;

import javax.inject.Inject;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public class EtaMapper {
    @Inject
    public EtaMapper() {
    }

    public com.example.twister.model.Eta transform(EtaRepresentation v) {
        com.example.twister.model.Eta eta = null;
        if (v != null) {
            eta = new com.example.twister.model.Eta(v.tripID, v.estimatedArrivalWindowBegin, v.estimatedArrivalWindowEnd);
        }
        return eta;
    }
}
