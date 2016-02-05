package com.example.twister.model;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class Thermostat extends Device {
    private boolean canCool;
    private float ambientTemperature;
    private float targetTemperature;

    public Thermostat(String deviceID, String locale, String softwareVersion, String structureID,
                      String name, String nameLong, String lastConnection, boolean isOnline,
                      boolean canCool, float ambientTemperature, float targetTemperature) {
        super(deviceID, locale, softwareVersion, structureID, name, nameLong, lastConnection, isOnline);
        this.canCool = canCool;
        this.ambientTemperature = ambientTemperature;
        this.targetTemperature = targetTemperature;
    }

    @Override
    public String toString() {
        return "Thermostat{" +
                "canCool='" + canCool + '\'' +
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
}