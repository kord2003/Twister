package com.example.twister.presenters;

import android.util.Log;

import com.example.twister.exception.DefaultErrorBundle;
import com.example.twister.exception.ErrorBundle;
import com.example.twister.interactor.AccessTokenUseCase;
import com.example.twister.interactor.DefaultSubscriber;
import com.example.twister.interactor.MetaDataUseCase;
import com.example.twister.model.AccessToken;
import com.example.twister.model.MetaData;
import com.example.twister.view.LoginView;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private final MetaDataUseCase metaDataUseCase;
    private final AccessTokenUseCase accessTokenUseCase;
    private LoginView view;

    @Inject
    public LoginPresenterImpl(MetaDataUseCase metaDataUseCase, AccessTokenUseCase accessTokenUseCase) {
        this.metaDataUseCase = metaDataUseCase;
        this.accessTokenUseCase = accessTokenUseCase;
    }

    @Override
    public void attach(LoginView view) {
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
        metaDataUseCase.unsubscribe();
        accessTokenUseCase.unsubscribe();
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

    public void loadMetaData() {
        metaDataUseCase.unsubscribe();
        metaDataUseCase.execute(new DefaultSubscriber<MetaData>() {

            @Override
            public void onError(Throwable e) {
                Timber.e("ERROR!", e);
                Log.e("TAG", ":::: metaDataUseCase " + e.getMessage(), e);
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }

            @Override
            public void onNext(MetaData metaData) {
                Timber.d("metaDataUseCase finished: " + metaData);
                if (view != null) {
                    view.handleMetaData(metaData);
                }
            }
        });
    }

    @Override
    public void loadAccessToken(String code) {
        accessTokenUseCase.unsubscribe();
        accessTokenUseCase.setCode(code);
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
                if (view != null) {
                    view.handleAccessTokenLoaded(accessToken);
                }
            }
        });
    }
}