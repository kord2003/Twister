package com.example.twister.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.twister.R;
import com.example.twister.model.Device;
import com.example.twister.model.SmokeDetector;
import com.example.twister.model.Structure;
import com.example.twister.model.Thermostat;
import com.example.twister.presenters.NestOverviewPresenter;
import com.example.twister.view.NestOverviewView;
import com.example.twister.view.activities.adapters.DevicesAdapter;
import com.example.twister.view.activities.listeners.RecyclerItemClickListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class NestOverviewActivity extends BaseActivity implements NestOverviewView {

    public static final String EXTRA_DEVICE_ID = "EXTRA_DEVICE_ID";
    // Views
    @Bind(R.id.lstStructures)
    protected RecyclerView lstStructures;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    private ProgressDialog progressDialog;

    // Adapters
    private DevicesAdapter devicesAdapter;

    // Presenters
    @Inject
    protected NestOverviewPresenter nestOverviewPresenter;

    // Plain fields
    private final List<Structure> structures = new ArrayList<>();
    private final List<Device> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initializePresenters();
        initializeAdapters();
        initializeProgressDialog();
    }

    private void initializeProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void initializeAdapters() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstStructures.setLayoutManager(linearLayoutManager);
        devicesAdapter = new DevicesAdapter(this, structures, devices);
        lstStructures.setAdapter(devicesAdapter);
        lstStructures.addItemDecoration(new StickyRecyclerHeadersDecoration(devicesAdapter));
        lstStructures.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Device device = devices.get(position);
                        openDeviceDetails(device);
                    }
                })
        );
    }

    private void openDeviceDetails(Device device) {
        Intent intent = null;
        if (device instanceof Thermostat) {
            intent = new Intent(this, ThermostatActivity.class);
        } else if (device instanceof SmokeDetector) {
            intent = new Intent(this, SmokeDetectorActivity.class);
        }
        if (intent != null) {
            intent.putExtra(EXTRA_DEVICE_ID, device.getDeviceId());
            startActivity(intent);
        }
    }

    private void initializePresenters() {
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nestOverviewPresenter.attach(this);
        nestOverviewPresenter.loadStructures();
    }

    @Override
    protected void onPause() {
        super.onPause();
        nestOverviewPresenter.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nestOverviewPresenter.destroy();
    }

    @Override
    public void handleStructures(List<Structure> data) {
        Timber.d("handleStructures: " + data.size());
        structures.clear();
        structures.addAll(data);
    }

    @Override
    public void handleDevices(List<Device> data) {
        Timber.d("handleDevices: " + data.size());
        devices.clear();
        devices.addAll(data);
        if (structures.size() > 0 && devices.size() > 0) {
            devicesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }
}