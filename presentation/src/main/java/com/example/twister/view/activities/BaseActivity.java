package com.example.twister.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.twister.AndroidApplication;
import com.example.twister.internal.di.HasComponent;
import com.example.twister.internal.di.components.ActivityComponent;
import com.example.twister.internal.di.components.ApplicationComponent;
import com.example.twister.internal.di.components.DaggerActivityComponent;
import com.example.twister.internal.di.modules.ActivityModule;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent> {
    private static final String TAG = BaseActivity.class.getName();
    protected FragmentManager fragmentManager;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        initializeActivityComponent();

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }
    }

    protected void enableBackNavigation(boolean isEnabled) {
        if (isEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    public void popToStartFragment() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();
        }
    }

    public void popFragment() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    protected void addFragmentToBackStack(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerViewId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
        enableBackNavigation(true);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    private void initializeActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule()).build();
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}