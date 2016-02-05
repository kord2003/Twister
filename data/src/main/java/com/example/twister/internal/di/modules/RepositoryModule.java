package com.example.twister.internal.di.modules;

import com.example.twister.repository.AuthDataRepository;
import com.example.twister.repository.AuthRepository;
import com.example.twister.repository.NestDataRepository;
import com.example.twister.repository.NestRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public NestRepository provideNestRepository(NestDataRepository impl) {
        return impl;
    }

    @Provides
    @Singleton
    public AuthRepository provideClientMetaDataRepository(AuthDataRepository impl) {
        return impl;
    }
}