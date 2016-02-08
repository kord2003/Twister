package com.example.twister.presenters;

import com.example.twister.exception.DefaultErrorBundle;
import com.example.twister.exception.ErrorBundle;
import com.example.twister.interactor.AccessTokenUseCase;
import com.example.twister.interactor.DefaultSubscriber;
import com.example.twister.interactor.GetThermostatUseCase;
import com.example.twister.model.AccessToken;
import com.example.twister.model.Thermostat;
import com.example.twister.view.ThermostatView;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by KorD on 06.02.2016.
 */
public class ThermostatPresenterImpl implements ThermostatPresenter {
    private final AccessTokenUseCase accessTokenUseCase;
    private final GetThermostatUseCase getThermostatUseCase;
    private ThermostatView view;

    @Inject
    public ThermostatPresenterImpl(AccessTokenUseCase accessTokenUseCase, GetThermostatUseCase getThermostatUseCase) {
        this.accessTokenUseCase = accessTokenUseCase;
        this.getThermostatUseCase = getThermostatUseCase;
    }

    @Override
    public void attach(ThermostatView view) {
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
        getThermostatUseCase.unsubscribe();
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
    public void loadThermostat(final String deviceId) {
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
                getThermostat(accessToken, deviceId);
            }
        });
    }

    private void getThermostat(AccessToken accessToken, String deviceId) {
        Timber.d(":::: getThermostatUseCase started");
        getThermostatUseCase.unsubscribe();
        getThermostatUseCase.setDeviceId(deviceId);
        getThermostatUseCase.execute(new DefaultSubscriber<Thermostat>() {
            @Override
            public void onError(Throwable e) {
                Timber.e(":::: getThermostatUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(Thermostat data) {
                Timber.d(":::: getThermostatUseCase finished: " + data);
                if (view != null) {
                    view.handleThermostat(data);
                }
            }
        });
    }
}