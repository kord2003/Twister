package com.example.twister.interactor;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.executor.ThreadExecutor;
import com.example.twister.model.Device;
import com.example.twister.model.Thermostat;
import com.example.twister.repository.NestRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by KorD on 06.02.2016.
 */
public class GetThermostatUseCase extends UseCase {
    private NestRepository nestRepository;
    private String deviceId;

    @Inject
    public GetThermostatUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
    }

    @Override
    protected Observable<Thermostat> buildUseCaseObservable() {
        return nestRepository.registerForDevicesUpdates().map(new Func1<List<Device>, Thermostat>() {
            @Override
            public Thermostat call(List<Device> devices) {
                Thermostat thermostat = null;
                for (Device device : devices) {
                    if (device.getDeviceId().equals(deviceId) && device instanceof Thermostat) {
                        thermostat = (Thermostat) device;
                        break;
                    }
                }
                return thermostat;
            }
        });
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}