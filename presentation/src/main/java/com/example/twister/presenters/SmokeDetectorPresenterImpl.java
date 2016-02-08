package com.example.twister.presenters;

import com.example.twister.exception.DefaultErrorBundle;
import com.example.twister.exception.ErrorBundle;
import com.example.twister.interactor.AccessTokenUseCase;
import com.example.twister.interactor.DefaultSubscriber;
import com.example.twister.interactor.GetSmokeDetectorUseCase;
import com.example.twister.model.AccessToken;
import com.example.twister.model.SmokeDetector;
import com.example.twister.view.SmokeDetectorView;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by KorD on 06.02.2016.
 */
public class SmokeDetectorPresenterImpl implements SmokeDetectorPresenter {
    private final AccessTokenUseCase accessTokenUseCase;
    private final GetSmokeDetectorUseCase getSmokeDetectorUseCase;
    private SmokeDetectorView view;

    @Inject
    public SmokeDetectorPresenterImpl(AccessTokenUseCase accessTokenUseCase, GetSmokeDetectorUseCase getSmokeDetectorUseCase) {
        this.accessTokenUseCase = accessTokenUseCase;
        this.getSmokeDetectorUseCase = getSmokeDetectorUseCase;
    }

    @Override
    public void attach(SmokeDetectorView view) {
        this.view = view;
    }

    @Override
    public void detach() {
        if (view != null) {
            view.hideProgress();
        }
        this.view = null;
    }

    @Override
    public void destroy() {
        accessTokenUseCase.unsubscribe();
        getSmokeDetectorUseCase.unsubscribe();
    }

    private void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    private void hideProgress() {
        if (view != null) {
            view.hideProgress();
        }
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        if (view != null) {
            String errorMessage = errorBundle.getErrorMessage();
            view.showError(errorMessage);
        }
    }

    @Override
    public void loadSmokeDetector(final String deviceId) {
        accessTokenUseCase.unsubscribe();
        accessTokenUseCase.setCode(null);
        accessTokenUseCase.execute(new DefaultSubscriber<AccessToken>() {

            @Override
            public void onError(Throwable e) {
                Timber.e(":::: accessTokenUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(AccessToken accessToken) {
                Timber.d(":::: accessTokenUseCase finished: " + accessToken);
                getSmokeDetector(accessToken, deviceId);
            }
        });
    }

    private void getSmokeDetector(AccessToken accessToken, String deviceId) {
        Timber.d(":::: getSmokeDetectorUseCase started: " + deviceId);
        getSmokeDetectorUseCase.unsubscribe();
        getSmokeDetectorUseCase.setDeviceId(deviceId);
        getSmokeDetectorUseCase.execute(new DefaultSubscriber<SmokeDetector>() {
            @Override
            public void onError(Throwable e) {
                Timber.e(":::: getSmokeDetectorUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(SmokeDetector data) {
                Timber.d(":::: getSmokeDetectorUseCase finished: " + data);
                if (view != null) {
                    view.handleSmokeDetector(data);
                }
            }
        });
    }
}