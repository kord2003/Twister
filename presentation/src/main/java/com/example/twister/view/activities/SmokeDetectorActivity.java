package com.example.twister.view.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
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

    private static final int BLINKING_DELAY = 500;
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

    // Animators
    private ValueAnimator coAlarmColorAnimation;
    private ValueAnimator smokeAlarmColorAnimation;

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
        SmokeDetector.AlarmState coAlarmState = data.getCoAlarmState();
        tvCOStatus.setText(coAlarmState.name());
        SmokeDetector.AlarmState smokeAlarmState = data.getSmokeAlarmState();
        tvSmokeStatus.setText(smokeAlarmState.name());

        int transparentColor = getResources().getColor(R.color.bg_detector);
        int okColor = getResources().getColor(R.color.bg_detector_green);
        int coAlarmColor = getAlarmColor(coAlarmState);
        cvCOStatus.setCardBackgroundColor(coAlarmColor);
        int smokeAlarmColor = getAlarmColor(smokeAlarmState);
        cvSmokeStatus.setCardBackgroundColor(smokeAlarmColor);
        if (coAlarmState != SmokeDetector.AlarmState.OK) {
            stopCOBlinking(coAlarmColor);
            startCOBlinking(coAlarmColor, transparentColor);
        } else {
            stopCOBlinking(okColor);
        }
        if (smokeAlarmState != SmokeDetector.AlarmState.OK) {
            stopSmokeBlinking(coAlarmColor);
            startSmokeBlinking(smokeAlarmColor, transparentColor);
        } else {
            stopSmokeBlinking(okColor);
        }
    }

    private void startCOBlinking(int colorFrom, int colorTo) {
        //if (coAlarmColorAnimation == null) {
            coAlarmColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            coAlarmColorAnimation.setDuration(BLINKING_DELAY); // milliseconds
            coAlarmColorAnimation.setRepeatMode(-1);
            coAlarmColorAnimation.setRepeatCount(Animation.INFINITE);
            coAlarmColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    cvCOStatus.setCardBackgroundColor((int) animator.getAnimatedValue());
                }
            });
        //}
        coAlarmColorAnimation.start();
    }

    private void stopCOBlinking(int color) {
        if (coAlarmColorAnimation != null) {
            coAlarmColorAnimation.end();
            coAlarmColorAnimation.cancel();
        }
        cvCOStatus.setCardBackgroundColor(color);
    }

    private void startSmokeBlinking(int colorFrom, int colorTo) {
        //if (smokeAlarmColorAnimation == null) {
            smokeAlarmColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            smokeAlarmColorAnimation.setDuration(BLINKING_DELAY); // milliseconds
            smokeAlarmColorAnimation.setRepeatMode(-1);
            smokeAlarmColorAnimation.setRepeatCount(Animation.INFINITE);
            smokeAlarmColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    cvSmokeStatus.setCardBackgroundColor((int) animator.getAnimatedValue());
                }
            });
        //}
        smokeAlarmColorAnimation.start();
    }

    private void stopSmokeBlinking(int color) {
        if (smokeAlarmColorAnimation != null) {
            smokeAlarmColorAnimation.end();
            smokeAlarmColorAnimation.cancel();
        }
        cvSmokeStatus.setCardBackgroundColor(color);
    }

    private int getAlarmColor(SmokeDetector.AlarmState alarmState) {
        Timber.d("getAlarmColor: " + alarmState.name());
        int color = 0xFFFFFFFF;
        switch (alarmState) {
            case OK:
                color = getResources().getColor(R.color.bg_detector_green);
                break;
            case WARNING:
                color = getResources().getColor(R.color.bg_detector_yellow);
                break;
            case EMERGENCY:
                color = getResources().getColor(R.color.bg_detector_red);
                break;
        }
        return color;
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