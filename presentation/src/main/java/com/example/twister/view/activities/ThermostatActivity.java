package com.example.twister.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.twister.R;
import com.example.twister.model.Thermostat;
import com.example.twister.presenters.ThermostatPresenter;
import com.example.twister.view.ThermostatView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.pawelkleczkowski.customgauge.CustomGauge;
import timber.log.Timber;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class ThermostatActivity extends BaseActivity implements ThermostatView {

    // Views
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.tvName)
    protected TextView tvName;
    @Bind(R.id.tvHumidity)
    protected TextView tvHumidity;
    @Bind(R.id.tvFanStatus)
    protected TextView tvFanStatus;
    @Bind(R.id.tvAmbientTemp)
    protected TextView tvAmbientTemp;
    @Bind(R.id.tempGauge)
    protected CustomGauge tempGauge;

    // Presenters
    @Inject
    protected ThermostatPresenter thermostatPresenter;

    // Plain fields
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
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
        thermostatPresenter.attach(this);
        thermostatPresenter.loadThermostat(deviceId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        thermostatPresenter.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thermostatPresenter.destroy();
    }

    @Override
    public void handleThermostat(Thermostat data) {
        Timber.d("handleThermostat: " + data);
        String name = data.getName();
        tvName.setText(name);
        String humidity = data.getHumidity() + "%";
        tvHumidity.setText(humidity);
        String hasFan = data.isHasFan() ? getString(R.string.on) : getString(R.string.off);
        tvFanStatus.setText(hasFan);
        int ambientTemp = (int) data.getAmbientTemperature();
        tempGauge.setValue(ambientTemp);
        String ambientTempString = data.getAmbientTemperature() + "°C";
        tvAmbientTemp.setText(ambientTempString);
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