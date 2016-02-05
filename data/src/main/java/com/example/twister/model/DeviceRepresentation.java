package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class DeviceRepresentation {
    @SerializedName("device_id")
    public String deviceID;

    @SerializedName("locale")
    public String locale;

    @SerializedName("software_version")
    public String softwareVersion;

    @SerializedName("structure_id")
    public String structureID;

    @SerializedName("name")
    public String name;

    @SerializedName("name_long")
    public String nameLong;

    @SerializedName("last_connection")
    public String lastConnection;

    @SerializedName("is_online")
    public boolean isOnline;
}