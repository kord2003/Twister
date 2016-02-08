package com.example.twister.presenters;

import com.example.twister.view.NestOverviewView;

/**
 * Created by sharlukovich on 04.02.2016.
 */
public interface NestOverviewPresenter {
    void attach(NestOverviewView view);

    void detach();

    void destroy();

    void loadStructures();
}