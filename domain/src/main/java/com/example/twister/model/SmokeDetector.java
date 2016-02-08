package com.example.twister.model;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class SmokeDetector extends Device {

    private String batteryHealth;
    private AlarmState coAlarmState;
    private AlarmState smokeAlarmState;

    public SmokeDetector(String deviceID, String locale, String softwareVersion, String structureID, String name, String nameLong, String lastConnection, boolean isOnline, String batteryHealth, AlarmState coAlarmState, AlarmState smokeAlarmState) {
        super(deviceID, locale, softwareVersion, structureID, name, nameLong, lastConnection, isOnline);
        this.batteryHealth = batteryHealth;
        this.coAlarmState = coAlarmState;
        this.smokeAlarmState = smokeAlarmState;
    }

    public String getBatteryHealth() {
        return batteryHealth;
    }

    public AlarmState getCoAlarmState() {
        return coAlarmState;
    }

    public AlarmState getSmokeAlarmState() {
        return smokeAlarmState;
    }

    @Override
    public String toString() {
        return "SmokeDetector{" +
                "coAlarmState='" + coAlarmState + '\'' +
                "smokeAlarmState='" + smokeAlarmState + '\'' +
                "} " + super.toString();
    }

    public enum AlarmState {
        OK, WARNING, EMERGENCY
    }
}