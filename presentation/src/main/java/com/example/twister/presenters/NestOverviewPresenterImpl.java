package com.example.twister.presenters;

import com.example.twister.exception.DefaultErrorBundle;
import com.example.twister.exception.ErrorBundle;
import com.example.twister.interactor.AccessTokenUseCase;
import com.example.twister.interactor.DefaultSubscriber;
import com.example.twister.interactor.GetAllDevicesUseCase;
import com.example.twister.interactor.GetAllStructuresUseCase;
import com.example.twister.model.AccessToken;
import com.example.twister.model.Device;
import com.example.twister.model.Structure;
import com.example.twister.view.NestOverviewView;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public class NestOverviewPresenterImpl implements NestOverviewPresenter {
    private final AccessTokenUseCase accessTokenUseCase;
    private final GetAllStructuresUseCase getAllStructuresUseCase;
    private final GetAllDevicesUseCase getAllDevicesUseCase;
    private NestOverviewView view;

    @Inject
    public NestOverviewPresenterImpl(AccessTokenUseCase accessTokenUseCase, GetAllStructuresUseCase getAllStructuresUseCase, GetAllDevicesUseCase getAllDevicesUseCase) {
        this.accessTokenUseCase = accessTokenUseCase;
        this.getAllStructuresUseCase = getAllStructuresUseCase;
        this.getAllDevicesUseCase = getAllDevicesUseCase;
    }

    @Override
    public void attach(NestOverviewView view) {
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
        getAllStructuresUseCase.unsubscribe();
        getAllDevicesUseCase.unsubscribe();
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
    public void loadStructures() {
        showProgress();
        accessTokenUseCase.unsubscribe();
        accessTokenUseCase.setCode(null);
        accessTokenUseCase.execute(new DefaultSubscriber<AccessToken>() {

            @Override
            public void onError(Throwable e) {
                hideProgress();
                Timber.e(":::: accessTokenUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(AccessToken accessToken) {
                Timber.d(":::: accessTokenUseCase finished: " + accessToken);
                getStructures(accessToken);
            }
        });
    }

    private void getStructures(final AccessToken accessToken) {
        getAllDevicesUseCase.unsubscribe();
        getAllStructuresUseCase.unsubscribe();
        getAllStructuresUseCase.setAccessToken(accessToken);
        getAllStructuresUseCase.execute(new DefaultSubscriber<List<Structure>>() {
            @Override
            public void onError(Throwable e) {
                hideProgress();
                Timber.e(":::: getAllStructuresUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(List<Structure> data) {
                Timber.d(":::: getAllStructuresUseCase finished: " + data.size());
                if (view != null) {
                    view.handleStructures(data);
                }
                getDevices(accessToken);
            }
        });
    }

    private void getDevices(AccessToken accessToken) {
        Timber.d(":::: getAllDevicesUseCase started");
        getAllDevicesUseCase.unsubscribe();
        //getAllDevicesUseCase.setAccessToken(accessToken);
        getAllDevicesUseCase.execute(new DefaultSubscriber<List<Device>>() {
            @Override
            public void onError(Throwable e) {
                hideProgress();
                Timber.e(":::: getAllDevicesUseCase " + e.getMessage());
                e.printStackTrace();
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(List<Device> data) {
                hideProgress();
                Timber.d(":::: getAllDevicesUseCase finished: " + data.size());
                if (view != null) {
                    view.handleDevices(data);
                }
            }
        });
    }
}