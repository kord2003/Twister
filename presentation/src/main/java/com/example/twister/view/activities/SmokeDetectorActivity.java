package com.example.twister.view.activities;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.twister.R;
import com.example.twister.model.SmokeDetector;
import com.example.twister.presenters.SmokeDetectorPresenter;
import com.example.twister.view.SmokeDetectorView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class SmokeDetectorActivity extends BaseActivity implements SmokeDetectorView {

    // Views
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.tvName)
    protected TextView tvName;
    @Bind(R.id.tvSmokeStatus)
    protected TextView tvSmokeStatus;
    @Bind(R.id.tvCOStatus)
    protected TextView tvCOStatus;
    @Bind(R.id.cvSmokeStatus)
    protected CardView cvSmokeStatus;
    @Bind(R.id.cvCOStatus)
    protected CardView cvCOStatus;

    // Presenters
    @Inject
    protected SmokeDetectorPresenter smokeDetectorPresenter;

    // Plain fields
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke_detector);
        deviceId = getIntent().getStringExtra(NestOverviewActivity.EXTRA_DEVICE_ID);
        Timber.d("onCreate: " + deviceId);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializePresenters();
        initializeAdapters();
    }

    private void initializeAdapters() {
    }

    private void initializePresenters() {
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        smokeDetectorPresenter.attach(this);
        smokeDetectorPresenter.loadSmokeDetector(deviceId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        smokeDetectorPresenter.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smokeDetectorPresenter.destroy();
    }

    @Override
    public void handleSmokeDetector(SmokeDetector data) {
        Timber.d("handleSmokeDetector: " + data);
        String name = data.getName();
        tvName.setText(name);
        String coAlarmState = data.getCoAlarmState();
        tvCOStatus.setText(coAlarmState);
        String smokeAlarmState = data.getSmokeAlarmState();
        tvSmokeStatus.setText(smokeAlarmState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        showToast(message);
    }
}