package com.example.twister.interactor;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.model.Structure;
import com.example.twister.repository.NestRepository;

import javax.inject.Inject;

import rx.Observable;

public class SelectedStructureUseCase extends UseCase {
    private NestRepository nestRepository;

    @Inject
    public SelectedStructureUseCase(NestRepository nestRepository, com.example.twister.executor.ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return nestRepository.registerForSelectedStructureUpdates();
    }

    public void setSelectedVehicle(Structure structure) {
        nestRepository.setSelectedStructure(structure);
    }
}