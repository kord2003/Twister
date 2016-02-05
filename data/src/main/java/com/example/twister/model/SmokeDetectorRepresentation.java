package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class SmokeDetectorRepresentation extends DeviceRepresentation {
    @SerializedName("battery_health")
    public String batteryHealth;
    @SerializedName("co_alarm_state")
    public String coAlarmState;
    @SerializedName("smoke_alarm_state")
    public String smokeAlarmState;
}