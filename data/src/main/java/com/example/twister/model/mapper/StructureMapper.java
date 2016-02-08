package com.example.twister.model.mapper;

import com.example.twister.model.Eta;
import com.example.twister.model.Structure;
import com.example.twister.model.StructureRepresentation;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sharlukovich on 04.02.2016.
 */
@Singleton
public class StructureMapper {

    private final EtaMapper etaMapper;

    @Inject
    public StructureMapper(EtaMapper etaMapper) {
        this.etaMapper = etaMapper;
    }

    public Structure transform(StructureRepresentation v) {
        Structure structure = null;
        if (v != null) {
            Eta eta = etaMapper.transform(v.eta);
            structure = new Structure(
                    v.structureId,
                    v.thermostats,
                    v.smokeDetectors,
                    v.name,
                    v.countryCode,
                    v.peakPeriodStartTime,
                    v.peakPeriodEndTime,
                    v.timeZone,
                    v.awayState,
                    eta
            );
        }
        return structure;
    }
}