package com.example.twister;

import android.app.Application;

import com.example.twister.internal.di.components.ApplicationComponent;
import com.example.twister.internal.di.components.DaggerApplicationComponent;
import com.example.twister.internal.di.modules.ApplicationModule;
import com.example.twister.log.LumberYard;

import timber.log.Timber;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            LumberYard.create(this);
            LumberYard.cleanUp();

            Timber.plant(new Timber.DebugTree());
            Timber.plant(LumberYard.tree());

        }

        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}