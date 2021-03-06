package com.example.twister.model.mapper;

import com.example.twister.model.Device;
import com.example.twister.model.DeviceRepresentation;
import com.example.twister.model.SmokeDetector;
import com.example.twister.model.SmokeDetectorRepresentation;
import com.example.twister.model.Thermostat;
import com.example.twister.model.ThermostatRepresentation;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sharlukovich on 05.02.2016.
 */

@Singleton
public class DeviceMapper {

    @Inject
    public DeviceMapper() {

    }

    public Device transform(DeviceRepresentation v) {
        Device device = null;
        if (v != null) {
            if (v instanceof ThermostatRepresentation) {
                ThermostatRepresentation tr = (ThermostatRepresentation) v;
                device = new Thermostat(
                        tr.deviceID,
                        tr.locale,
                        tr.softwareVersion,
                        tr.structureID,
                        tr.name,
                        tr.nameLong,
                        tr.lastConnection,
                        tr.isOnline,
                        tr.canCool,
                        tr.ambientTemperature,
                        tr.targetTemperature,
                        tr.hasFan,
                        tr.humidity
                );
            } else if (v instanceof SmokeDetectorRepresentation) {
                SmokeDetectorRepresentation sdr = (SmokeDetectorRepresentation) v;

                SmokeDetector.AlarmState coAlarmState = SmokeDetector.AlarmState.OK;
                if (sdr.coAlarmState != null) {
                    coAlarmState = SmokeDetector.AlarmState.valueOf(sdr.coAlarmState.toUpperCase());
                }
                SmokeDetector.AlarmState smokeAlarmState = SmokeDetector.AlarmState.OK;
                if (sdr.smokeAlarmState != null) {
                    smokeAlarmState = SmokeDetector.AlarmState.valueOf(sdr.smokeAlarmState.toUpperCase());
                }
                device = new SmokeDetector(
                        sdr.deviceID,
                        sdr.locale,
                        sdr.softwareVersion,
                        sdr.structureID,
                        sdr.name,
                        sdr.nameLong,
                        sdr.lastConnection,
                        sdr.isOnline,
                        sdr.batteryHealth,
                        coAlarmState,
                        smokeAlarmState
                );
            } else if (v instanceof DeviceRepresentation) {
                device = new Device(
                        v.deviceID,
                        v.locale,
                        v.softwareVersion,
                        v.structureID,
                        v.name,
                        v.nameLong,
                        v.lastConnection,
                        v.isOnline
                );
            }
        }
        return device;
    }
}