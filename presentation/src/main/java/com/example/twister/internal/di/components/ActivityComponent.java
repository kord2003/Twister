package com.example.twister.internal.di.components;

import com.example.twister.internal.di.modules.ActivityModule;
import com.example.twister.view.activities.LoginActivity;
import com.example.twister.view.activities.NestOverviewActivity;

import dagger.Component;

@com.example.twister.internal.di.PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    void inject(NestOverviewActivity nestOverviewActivity);
}