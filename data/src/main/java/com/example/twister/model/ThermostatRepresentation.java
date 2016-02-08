package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class ThermostatRepresentation extends DeviceRepresentation {
    @SerializedName("can_cool")
    public boolean canCool;
    @SerializedName("ambient_temperature_c")
    public float ambientTemperature;
    @SerializedName("target_temperature_c")
    public float targetTemperature;
    @SerializedName("has_fan")
    public boolean hasFan;
    @SerializedName("humidity")
    public float humidity;
}
