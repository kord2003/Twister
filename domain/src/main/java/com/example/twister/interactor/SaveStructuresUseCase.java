package com.example.twister.interactor;

import com.example.twister.repository.NestRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class SaveStructuresUseCase extends UseCase {
    private NestRepository nestRepository;
    private final List<com.example.twister.model.Structure> structures = new ArrayList<>();

    private com.example.twister.model.Structure structure = null;

    @Inject
    public SaveStructuresUseCase(NestRepository nestRepository, com.example.twister.executor.ThreadExecutor threadExecutor, com.example.twister.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
    }

    public void setStructures(List<com.example.twister.model.Structure> data) {
        structures.clear();
        structures.addAll(data);
    }

    public void setStructure(com.example.twister.model.Structure structure) {
        this.structure = structure;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return nestRepository.updateStructure(structure);
    }
}