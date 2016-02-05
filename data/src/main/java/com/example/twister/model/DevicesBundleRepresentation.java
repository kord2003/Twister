package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class DevicesBundleRepresentation {
    @SerializedName("thermostats")
    public List<DeviceRepresentation> thermostats;
    @SerializedName("smoke_co_alarms")
    public List<DeviceRepresentation> smokeCOAlarms;
    @SerializedName("cameras")
    public List<DeviceRepresentation> cameras;
}