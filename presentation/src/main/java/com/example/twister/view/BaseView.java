package com.example.twister.view;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}