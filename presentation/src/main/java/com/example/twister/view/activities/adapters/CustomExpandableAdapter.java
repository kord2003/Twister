package com.example.twister.view.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twister.R;
import com.example.twister.model.Device;
import com.example.twister.model.SmokeDetector;
import com.example.twister.model.Structure;
import com.example.twister.model.Thermostat;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import timber.log.Timber;

/**
 * Created by sharlukovich on 05.02.2016.
 */
public class CustomExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Device> devices;
    private final List<Structure> structures;

    private enum DeviceCardViewType {
        THERMOSTAT,
        SMOKE_DETECTOR;

        public static final DeviceCardViewType values[] = values();
    }

    public CustomExpandableAdapter(Context context, List<Structure> structures, List<Device> dataSet) {
        this.context = context;
        this.structures = structures;
        this.devices = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        switch (DeviceCardViewType.values[viewType]) {
            case THERMOSTAT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_thermostat_overview, parent, false);
                return new ThermostatViewHolder(v);

            case SMOKE_DETECTOR:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_smoke_detector_overview, parent, false);
                return new SmokeDetectorViewHolder(v);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        int viewType = getItemViewType(position);
        switch (DeviceCardViewType.values[viewType]) {
            case THERMOSTAT:
                ThermostatViewHolder eavh = (ThermostatViewHolder) viewHolder;
                Thermostat thermostat = (Thermostat) devices.get(position);
                eavh.tvName.setText(thermostat.getName());
                eavh.tvValue.setText("T: " + thermostat.getAmbientTemperature() + "°C");
                break;

            case SMOKE_DETECTOR:
                SmokeDetectorViewHolder mavh = (SmokeDetectorViewHolder) viewHolder;
                SmokeDetector smokeDetector = (SmokeDetector) devices.get(position);
                mavh.tvName.setText(smokeDetector.getName());
                mavh.tvValue.setText("CO: " + smokeDetector.getCoAlarmState() + "\nSmk: " + smokeDetector.getSmokeAlarmState());
                break;
        }
    }

    public Structure getStructure(int position) {
        int viewType = getItemViewType(position);
        Structure structure = null;
        switch (DeviceCardViewType.values[viewType]) {
            case THERMOSTAT:
                Thermostat thermostat = (Thermostat) devices.get(position);
                structure = getStructureByDeviceId(thermostat.getDeviceID());
                break;

            case SMOKE_DETECTOR:
                SmokeDetector smokeDetector = (SmokeDetector) devices.get(position);
                structure = getStructureByDeviceId(smokeDetector.getDeviceID());
                break;
        }
        return structure;
    }

    private Structure getStructureByDeviceId(String deviceID) {
        for (Structure structure : structures) {
            for (String id : structure.getThermostatIDs()) {
                if (deviceID.equals(id)) {
                    return structure;
                }
            }
            for (String id : structure.getSmokeCOAlarms()) {
                if (deviceID.equals(id)) {
                    return structure;
                }
            }
        }
        return null;
    }

    @Override
    public long getHeaderId(int position) {
        Structure structure = getStructure(position);
        long headerId = structures.indexOf(structure);
        Timber.d("headerId = " + headerId);
        return headerId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_structure_overview, parent, false);
        return new StructureViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tvName = ((StructureViewHolder) holder).tvName;
        Structure structure = getStructure(position);
        tvName.setText(structure.getName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = devices.get(position);
        Timber.d("getItemViewType: " + item);
        if (item instanceof Thermostat) {
            return DeviceCardViewType.THERMOSTAT.ordinal();
        } else if (item instanceof SmokeDetector) {
            return DeviceCardViewType.SMOKE_DETECTOR.ordinal();
        }

        return -1;
    }

    public static class ThermostatViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvValue;

        public ThermostatViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
        }
    }

    public static class SmokeDetectorViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvValue;

        public SmokeDetectorViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
        }
    }

    public static class StructureViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvValue;

        public StructureViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
        }
    }
}