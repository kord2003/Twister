package com.example.twister.internal.di.modules;

import android.app.Activity;

import com.example.twister.internal.di.PerActivity;
import com.example.twister.presenters.LoginPresenter;
import com.example.twister.presenters.LoginPresenterImpl;
import com.example.twister.presenters.NestOverviewPresenter;
import com.example.twister.presenters.NestOverviewPresenterImpl;
import com.example.twister.presenters.SmokeDetectorPresenter;
import com.example.twister.presenters.SmokeDetectorPresenterImpl;
import com.example.twister.presenters.ThermostatPresenter;
import com.example.twister.presenters.ThermostatPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public LoginPresenter provideLoginPresenter(LoginPresenterImpl impl) {
        return impl;
    }

    @Provides
    @PerActivity
    public NestOverviewPresenter provideNestOverviewPresenter(NestOverviewPresenterImpl impl) {
        return impl;
    }

    @Provides
    @PerActivity
    public ThermostatPresenter provideThermostatPresenter(ThermostatPresenterImpl impl) {
        return impl;
    }

    @Provides
    @PerActivity
    public SmokeDetectorPresenter provideSmokeDetectorPresenter(SmokeDetectorPresenterImpl impl) {
        return impl;
    }
}