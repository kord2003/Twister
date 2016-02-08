package com.example.twister.presenters;

import com.example.twister.view.SmokeDetectorView;

/**
 * Created by KorD on 06.02.2016.
 */
public interface SmokeDetectorPresenter {
    void attach(SmokeDetectorView view);

    void detach();

    void destroy();

    void loadSmokeDetector(String deviceId);
}