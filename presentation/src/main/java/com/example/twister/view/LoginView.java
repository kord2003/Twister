package com.example.twister.view;

import com.example.twister.model.AccessToken;
import com.example.twister.model.ClientMetaData;

/**
 * Created by sharlukovich on 02.02.2016.
 */
public interface LoginView extends BaseView {
    void handleClientMetaData(ClientMetaData clientMetaData);

    void handleAccessTokenLoaded(AccessToken accessToken);
}