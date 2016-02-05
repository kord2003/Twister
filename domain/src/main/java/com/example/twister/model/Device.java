package com.example.twister.model;

import java.util.Comparator;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class Device {
    private String deviceID;
    private String locale;
    private String softwareVersion;
    private String structureID;
    private String name;
    private String nameLong;
    private String lastConnection;
    private boolean isOnline;

    public Device(String deviceID, String locale, String softwareVersion, String structureID, String name, String nameLong, String lastConnection, boolean isOnline) {
        this.deviceID = deviceID;
        this.locale = locale;
        this.softwareVersion = softwareVersion;
        this.structureID = structureID;
        this.name = name;
        this.nameLong = nameLong;
        this.lastConnection = lastConnection;
        this.isOnline = isOnline;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getLocale() {
        return locale;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public String getStructureID() {
        return structureID;
    }

    public String getName() {
        return name;
    }

    public String getNameLong() {
        return nameLong;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceID='" + deviceID + '\'' +
                ", name='" + name + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }


    public static class DeviceComparator implements Comparator<Device> {
        @Override
        public int compare(Device o1, Device o2) {
            return o2.getStructureID().compareTo(o1.getStructureID());
        }
    }
}