package com.example.twister.view.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.twister.R;
import com.example.twister.model.Device;
import com.example.twister.model.Structure;
import com.example.twister.presenters.NestOverviewPresenter;
import com.example.twister.view.NestOverviewView;
import com.example.twister.view.activities.adapters.CustomExpandableAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class NestOverviewActivity extends BaseActivity implements NestOverviewView {

    // Views
    @Bind(R.id.lstStructures)
    protected RecyclerView lstStructures;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    // Adapters
    private CustomExpandableAdapter customExpandableAdapter;

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
    }

    private void initializeAdapters() {

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstStructures.setLayoutManager(linearLayoutManager);
        customExpandableAdapter = new CustomExpandableAdapter(this, structures, devices);
        lstStructures.setAdapter(customExpandableAdapter);
        lstStructures.addItemDecoration(new StickyRecyclerHeadersDecoration(customExpandableAdapter));
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
            customExpandableAdapter.notifyDataSetChanged();
        }
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