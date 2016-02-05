package com.example.twister.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sharlukovich on 03.02.2016.
 */

public class Structure {
    public static final String STATE_AWAY = "away";
    public static final String STATE_AUTO_AWAY = "auto-away";
    public static final String STATE_HOME = "home";

    private String structureID;

    private List<String> thermostatIDs;

    private List<String> smokeCOAlarms;

    private String name;

    private String countryCode;

    private String peakPeriodStartTime;

    private String peakPeriodEndTime;

    private String timeZone;

    private String awayState;

    private Eta eta;

    public Structure(String structureID, List<String> thermostatIDs, List<String> smokeCOAlarms, String name, String countryCode, String peakPeriodStartTime, String peakPeriodEndTime, String timeZone, String awayState, Eta eta) {
        this.structureID = structureID;
        this.thermostatIDs = thermostatIDs;
        if (this.thermostatIDs == null) {
            this.thermostatIDs = new ArrayList<>();
        }
        this.smokeCOAlarms = smokeCOAlarms;
        if (this.smokeCOAlarms == null) {
            this.smokeCOAlarms = new ArrayList<>();
        }
        this.name = name;
        this.countryCode = countryCode;
        this.peakPeriodStartTime = peakPeriodStartTime;
        this.peakPeriodEndTime = peakPeriodEndTime;
        this.timeZone = timeZone;
        this.awayState = awayState;
        this.eta = eta;
    }

    public String getStructureID() {
        return structureID;
    }

    public List<String> getThermostatIDs() {
        return thermostatIDs;
    }

    public List<String> getSmokeCOAlarms() {
        return smokeCOAlarms;
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
                "structureID='" + structureID + '\'' +
                ", name='" + name + '\'' +
                ", awayState='" + awayState + '\'' +
                '}';
    }

    public static class StructureComparator implements Comparator<Structure> {
        @Override
        public int compare(Structure o1, Structure o2) {
            return o2.getStructureID().compareTo(o1.getStructureID());
        }
    }
}