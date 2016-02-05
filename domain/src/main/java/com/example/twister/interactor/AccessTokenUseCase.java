package com.example.twister.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class AccessTokenUseCase extends UseCase {
    private com.example.twister.repository.AuthRepository authRepository;
    private String code;

    @Inject
    public AccessTokenUseCase(com.example.twister.repository.AuthRepository authRepository, com.example.twister.executor.ThreadExecutor threadExecutor, com.example.twister.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authRepository = authRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authRepository.registerForAccessTokenUpdates(code);
    }

    public void setCode(String code) {
        this.code = code;
    }
}