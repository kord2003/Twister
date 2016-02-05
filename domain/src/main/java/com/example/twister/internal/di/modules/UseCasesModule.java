package com.example.twister.internal.di.modules;

import com.example.twister.executor.PostExecutionThread;
import com.example.twister.executor.ThreadExecutor;
import com.example.twister.interactor.AccessTokenUseCase;
import com.example.twister.interactor.ClientMetaDataUseCase;
import com.example.twister.interactor.GetAllDevicesUseCase;
import com.example.twister.interactor.GetAllStructuresUseCase;
import com.example.twister.interactor.SaveStructuresUseCase;
import com.example.twister.interactor.SelectedStructureUseCase;
import com.example.twister.internal.di.PerActivity;
import com.example.twister.repository.AuthRepository;
import com.example.twister.repository.NestRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCasesModule {

    @Provides
    @PerActivity
    GetAllStructuresUseCase provideGetAllStructuresUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetAllStructuresUseCase(nestRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    GetAllDevicesUseCase provideGetAllDevicesUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetAllDevicesUseCase(nestRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    SelectedStructureUseCase provideSelectedStructureUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SelectedStructureUseCase(nestRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    SaveStructuresUseCase provideSaveStructuresUseCase(NestRepository nestRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SaveStructuresUseCase(nestRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    ClientMetaDataUseCase provideClientMetaDataUseCase(AuthRepository authRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new ClientMetaDataUseCase(authRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    AccessTokenUseCase provideAccessTokenUseCase(AuthRepository authRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new AccessTokenUseCase(authRepository, threadExecutor, postExecutionThread);
    }
}