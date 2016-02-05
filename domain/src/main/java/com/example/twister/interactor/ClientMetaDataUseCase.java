package com.example.twister.interactor;

import com.example.twister.repository.AuthRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class ClientMetaDataUseCase extends UseCase {
    private AuthRepository authRepository;

    @Inject
    public ClientMetaDataUseCase(AuthRepository authRepository, com.example.twister.executor.ThreadExecutor threadExecutor, com.example.twister.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authRepository = authRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authRepository.registerForClientMetaDataUpdates();
    }
}