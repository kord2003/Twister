package com.example.twister.presenters;

import com.example.twister.view.LoginView;

/**
 * Created by sharlukovich on 02.02.2016.
 */
public interface LoginPresenter {

    void attach(LoginView view);

    void detach();

    void destroy();

    void loadClientMetaData();

    void loadAccessToken(String code);
}