package com.example.twister.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sharlukovich on 03.02.2016.
 */

public class Structure {

    private String structureId;

    private List<String> thermostats;

    private List<String> smokeDetectors;

    private String name;

    private String countryCode;

    private String peakPeriodStartTime;

    private String peakPeriodEndTime;

    private String timeZone;

    private String awayState;

    private Eta eta;

    public Structure(String structureId, List<String> thermostats, List<String> smokeDetectors, String name, String countryCode, String peakPeriodStartTime, String peakPeriodEndTime, String timeZone, String awayState, Eta eta) {
        this.structureId = structureId;
        this.thermostats = thermostats;
        if (this.thermostats == null) {
            this.thermostats = new ArrayList<>();
        }
        this.smokeDetectors = smokeDetectors;
        if (this.smokeDetectors == null) {
            this.smokeDetectors = new ArrayList<>();
        }
        this.name = name;
        this.countryCode = countryCode;
        this.peakPeriodStartTime = peakPeriodStartTime;
        this.peakPeriodEndTime = peakPeriodEndTime;
        this.timeZone = timeZone;
        this.awayState = awayState;
        this.eta = eta;
    }

    public String getStructureId() {
        return structureId;
    }

    public List<String> getThermostats() {
        return thermostats;
    }

    public List<String> getSmokeDetectors() {
        return smokeDetectors;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPeakPeriodStartTime() {
        return peakPeriodStartTime;
    }

    public String getPeakPeriodEndTime() {
        return peakPeriodEndTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getAwayState() {
        return awayState;
    }

    public Eta getEta() {
        return eta;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "structureId='" + structureId + '\'' +
                ", name='" + name + '\'' +
                ", awayState='" + awayState + '\'' +
                '}';
    }

    public static class StructureComparator implements Comparator<Structure> {
        @Override
        public int compare(Structure o1, Structure o2) {
            return o2.getStructureId().compareTo(o1.getStructureId());
        }
    }
}