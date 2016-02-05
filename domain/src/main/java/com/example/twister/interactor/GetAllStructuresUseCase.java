package com.example.twister.interactor;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.executor.ThreadExecutor;
import com.example.twister.model.AccessToken;
import com.example.twister.model.Structure;
import com.example.twister.repository.NestRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class GetAllStructuresUseCase extends UseCase {
    private NestRepository nestRepository;
    private AccessToken accessToken;
    private Structure.StructureComparator comparator;

    @Inject
    public GetAllStructuresUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nestRepository = nestRepository;
        this.comparator = new Structure.StructureComparator();
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    protected Observable<List<Structure>> buildUseCaseObservable() {
        return nestRepository.registerForStructuresUpdates(accessToken).map(new Func1<List<Structure>, List<Structure>>() {
            @Override
            public List<Structure> call(List<Structure> structures) {
                Collections.sort(structures, comparator);
                return structures;
            }
        });
    }
}