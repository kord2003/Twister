package com.example.twister.view.activities.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
public class DevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Device> devices;
    private final List<Structure> structures;

    private enum DeviceCardViewType {
        THERMOSTAT,
        SMOKE_DETECTOR;

        public static final DeviceCardViewType values[] = values();
    }

    public DevicesAdapter(Context context, List<Structure> structures, List<Device> dataSet) {
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
                ThermostatViewHolder tvh = (ThermostatViewHolder) viewHolder;
                Thermostat thermostat = (Thermostat) devices.get(position);
                tvh.tvName.setText(thermostat.getName());
                tvh.tvValue.setText("T: " + thermostat.getAmbientTemperature() + "°C");
                break;

            case SMOKE_DETECTOR:
                SmokeDetectorViewHolder sdvh = (SmokeDetectorViewHolder) viewHolder;
                SmokeDetector smokeDetector = (SmokeDetector) devices.get(position);
                SmokeDetector.AlarmState coAlarmState = smokeDetector.getCoAlarmState();
                SmokeDetector.AlarmState smokeAlarmState = smokeDetector.getSmokeAlarmState();
                sdvh.tvName.setText(smokeDetector.getName());
                sdvh.tvValue.setText("CO: " + coAlarmState.name() + "\nSmk: " + smokeAlarmState.name());

                int transparentColor = context.getResources().getColor(R.color.bg_detector);
                if (smokeAlarmState == SmokeDetector.AlarmState.EMERGENCY || coAlarmState == SmokeDetector.AlarmState.EMERGENCY) {
                    int alarmColor = context.getResources().getColor(R.color.bg_detector_red);
                    sdvh.stopBlinking(alarmColor);
                    sdvh.startBlinking(alarmColor, transparentColor);
                } else if (smokeAlarmState == SmokeDetector.AlarmState.WARNING || coAlarmState == SmokeDetector.AlarmState.WARNING) {
                    int alarmColor = context.getResources().getColor(R.color.bg_detector_yellow);
                    sdvh.stopBlinking(alarmColor);
                    sdvh.startBlinking(alarmColor, transparentColor);
                } else {
                    int okColor = context.getResources().getColor(R.color.bg_detector_green);
                    sdvh.stopBlinking(okColor);
                }

                break;
        }
    }

    public Structure getStructure(int position) {
        int viewType = getItemViewType(position);
        Structure structure = null;
        switch (DeviceCardViewType.values[viewType]) {
            case THERMOSTAT:
                Thermostat thermostat = (Thermostat) devices.get(position);
                structure = getStructureByDeviceId(thermostat.getDeviceId());
                break;

            case SMOKE_DETECTOR:
                SmokeDetector smokeDetector = (SmokeDetector) devices.get(position);
                structure = getStructureByDeviceId(smokeDetector.getDeviceId());
                break;
        }
        return structure;
    }

    private Structure getStructureByDeviceId(String deviceId) {
        for (Structure structure : structures) {
            for (String id : structure.getThermostats()) {
                if (deviceId.equals(id)) {
                    return structure;
                }
            }
            for (String id : structure.getSmokeDetectors()) {
                if (deviceId.equals(id)) {
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
        final CardView cvAlarmStatus;
        private ValueAnimator alarmColorAnimation;
        private static final int BLINKING_DELAY = 500;

        public SmokeDetectorViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
            cvAlarmStatus = (CardView) v.findViewById(R.id.cvAlarmStatus);
        }

        public void startBlinking(int colorFrom, int colorTo) {
            //if (alarmColorAnimation == null) {
                alarmColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                alarmColorAnimation.setDuration(BLINKING_DELAY); // milliseconds
                alarmColorAnimation.setRepeatMode(-1);
                alarmColorAnimation.setRepeatCount(Animation.INFINITE);
                alarmColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        cvAlarmStatus.setCardBackgroundColor((int) animator.getAnimatedValue());
                    }
                });
            //}
            alarmColorAnimation.start();
        }

        public void stopBlinking(int color) {
            if (alarmColorAnimation != null) {
                alarmColorAnimation.end();
                alarmColorAnimation.cancel();
            }
            cvAlarmStatus.setCardBackgroundColor(color);
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