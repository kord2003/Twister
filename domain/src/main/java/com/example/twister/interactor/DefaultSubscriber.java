package com.example.twister.interactor;

import rx.Subscriber;

public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {}

    @Override public void onNext(T t) {}
}