package com.example.twister.view;

import com.example.twister.model.AccessToken;
import com.example.twister.model.MetaData;

/**
 * Created by sharlukovich on 02.02.2016.
 */
public interface LoginView extends BaseView {
    void handleMetaData(MetaData metaData);

    void handleAccessTokenLoaded(AccessToken accessToken);
}