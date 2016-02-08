package com.example.twister.interactor;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.executor.ThreadExecutor;
import com.example.twister.model.Device;
import com.example.twister.model.SmokeDetector;
import com.example.twister.repository.NestRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by KorD on 06.02.2016.
 */
public class GetSmokeDetectorUseCase extends UseCase {
    private NestRepository nestRepository;
    private String deviceId;

    @Inject
    public GetSmokeDetectorUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
    }

    @Override
    protected Observable<SmokeDetector> buildUseCaseObservable() {
        return nestRepository.registerForDevicesUpdates().map(new Func1<List<Device>, SmokeDetector>() {
            @Override
            public SmokeDetector call(List<Device> devices) {
                SmokeDetector smokeDetector = null;
                for (Device device : devices) {
                    if (device.getDeviceId().equals(deviceId) && device instanceof SmokeDetector) {
                        smokeDetector = (SmokeDetector) device;
                        break;
                    }
                }
                return smokeDetector;
            }
        });
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}