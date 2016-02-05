package com.example.twister.model;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class SmokeDetector extends Device {
    private String batteryHealth;
    public String coAlarmState;
    public String smokeAlarmState;

    public SmokeDetector(String deviceID, String locale, String softwareVersion, String structureID, String name, String nameLong, String lastConnection, boolean isOnline, String batteryHealth, String coAlarmState, String smokeAlarmState) {
        super(deviceID, locale, softwareVersion, structureID, name, nameLong, lastConnection, isOnline);
        this.batteryHealth = batteryHealth;
        this.coAlarmState = coAlarmState;
        this.smokeAlarmState = smokeAlarmState;
    }

    public String getBatteryHealth() {
        return batteryHealth;
    }

    public String getCoAlarmState() {
        return coAlarmState;
    }

    public String getSmokeAlarmState() {
        return smokeAlarmState;
    }

    @Override
    public String toString() {
        return "SmokeDetector{" +
                "coAlarmState='" + coAlarmState + '\'' +
                "smokeAlarmState='" + smokeAlarmState + '\'' +
                "} " + super.toString();
    }
}