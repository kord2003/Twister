package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sharlukovich on 03.02.2016.
 */

public class StructureRepresentation {

    @SerializedName("structure_id")
    public String structureId;

    @SerializedName("thermostats")
    public List<String> thermostats;

    @SerializedName("smoke_co_alarms")
    public List<String> smokeDetectors;

    @SerializedName("name")
    public String name;

    @SerializedName("country_code")
    public String countryCode;

    @SerializedName("peak_period_start_time")
    public String peakPeriodStartTime;

    @SerializedName("peak_period_end_time")
    public String peakPeriodEndTime;

    @SerializedName("time_zone")
    public String timeZone;

    @SerializedName("away")
    public String awayState;

    @SerializedName("eta")
    public EtaRepresentation eta;
}