package com.example.twister.presenters;

import com.example.twister.view.ThermostatView;

/**
 * Created by KorD on 06.02.2016.
 */
public interface ThermostatPresenter {
    void attach(ThermostatView view);

    void detach();

    void destroy();

    void loadThermostat(String deviceId);
}