package com.example.twister.view;

import com.example.twister.model.Device;
import com.example.twister.model.Structure;

import java.util.List;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public interface NestOverviewView extends BaseView {
    void handleStructures(List<Structure> structures);
    void handleDevices(List<Device> devices);
}