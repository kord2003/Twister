package com.example.twister.model;

import java.util.Comparator;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class Device {
    private String deviceId;
    private String locale;
    private String softwareVersion;
    private String structureId;
    private String name;
    private String nameLong;
    private String lastConnection;
    private boolean isOnline;

    public Device(String deviceId, String locale, String softwareVersion, String structureId, String name, String nameLong, String lastConnection, boolean isOnline) {
        this.deviceId = deviceId;
        this.locale = locale;
        this.softwareVersion = softwareVersion;
        this.structureId = structureId;
        this.name = name;
        this.nameLong = nameLong;
        this.lastConnection = lastConnection;
        this.isOnline = isOnline;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getLocale() {
        return locale;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public String getStructureId() {
        return structureId;
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
                "deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }


    public static class DeviceComparator implements Comparator<Device> {
        @Override
        public int compare(Device o1, Device o2) {
            return o2.getStructureId().compareTo(o1.getStructureId());
        }
    }
}