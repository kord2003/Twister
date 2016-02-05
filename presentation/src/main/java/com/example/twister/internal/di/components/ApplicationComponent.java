package com.example.twister.internal.di.components;

import android.content.SharedPreferences;

import com.example.twister.internal.di.modules.ApplicationModule;
import com.example.twister.repository.NestRepository;
import com.example.twister.view.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    com.example.twister.executor.ThreadExecutor threadExecutor();

    com.example.twister.executor.PostExecutionThread postExecutionThread();

    SharedPreferences defaultSharedPreferences();

    NestRepository vehicleRepository();

    com.example.twister.repository.AuthRepository clientMetaDataRepository();
}