package com.example.twister.model;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class Thermostat extends Device {
    private boolean canCool;
    private float ambientTemperature;
    private float targetTemperature;
    private boolean hasFan;
    private float humidity;

    public Thermostat(String deviceId, String locale, String softwareVersion, String structureId, String name, String nameLong, String lastConnection, boolean isOnline, boolean canCool, float ambientTemperature, float targetTemperature, boolean hasFan, float humidity) {
        super(deviceId, locale, softwareVersion, structureId, name, nameLong, lastConnection, isOnline);
        this.canCool = canCool;
        this.ambientTemperature = ambientTemperature;
        this.targetTemperature = targetTemperature;
        this.hasFan = hasFan;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Thermostat{" +
                "canCool=" + canCool +
                ", ambientTemperature=" + ambientTemperature +
                ", targetTemperature=" + targetTemperature +
                ", hasFan=" + hasFan +
                ", humidity=" + humidity +
                "} " + super.toString();
    }

    public boolean isCanCool() {
        return canCool;
    }

    public float getAmbientTemperature() {
        return ambientTemperature;
    }

    public float getTargetTemperature() {
        return targetTemperature;
    }

    public boolean isHasFan() {
        return hasFan;
    }

    public float getHumidity() {
        return humidity;
    }
}