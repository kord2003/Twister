package com.example.twister.interactor;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.executor.ThreadExecutor;
import com.example.twister.model.Device;
import com.example.twister.repository.NestRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class GetAllDevicesUseCase extends UseCase {
    private NestRepository nestRepository;
    private Device.DeviceComparator comparator;

    @Inject
    public GetAllDevicesUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
        this.comparator = new Device.DeviceComparator();
    }

    @Override
    protected Observable<List<Device>> buildUseCaseObservable() {
        return nestRepository.registerForDevicesUpdates().map(new Func1<List<Device>, List<Device>>() {
            @Override
            public List<Device> call(List<Device> devices) {
                Collections.sort(devices, comparator);
                return devices;
            }
        });
    }
}