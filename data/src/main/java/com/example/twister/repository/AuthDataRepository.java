package com.example.twister.repository;

import com.example.twister.interactor.DefaultSubscriber;
import com.example.twister.model.AccessToken;
import com.example.twister.model.AccessTokenRepresentation;
import com.example.twister.model.EmptyBody;
import com.example.twister.model.MetaData;
import com.example.twister.model.mapper.AccessTokenMapper;
import com.example.twister.rest.AuthApi;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class AuthDataRepository implements AuthRepository {
    private static final String CLIENT_ID = "25d255b8-3430-4cc3-855e-87824b646eca";
    private static final String CLIENT_SECRET = "Hd6fI630LY0arA0IaQnQX7ZEs";
    private static final String REDIRECT_URL = "https://google.com/";

    private final AuthApi authApi;
    private final AccessTokenMapper accessTokenMapper;

    private MetaData metaData;

    private BehaviorSubject<AccessToken> accessTokenSubject;

    private AccessToken accessToken;
    private Subscription loadAccessTokenSubscription;

    @Inject
    public AuthDataRepository(AuthApi authApi, AccessTokenMapper accessTokenMapper) {
        this.authApi = authApi;
        this.accessTokenMapper = accessTokenMapper;
        this.accessTokenSubject = BehaviorSubject.create();
    }

    @Override
    public synchronized Observable<MetaData> registerForMetaDataUpdates() {
        Timber.d("registerForMetaDataUpdates");
        if (metaData == null) {
            metaData = new MetaData(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL);
        }
        return Observable.just(metaData);
    }

    @Override
    public synchronized Observable<AccessToken> registerForAccessTokenUpdates(String code) {
        Timber.d("registerForAccessTokenUpdates");
        if (loadAccessTokenSubscription == null || loadAccessTokenSubscription.isUnsubscribed()) {
            Timber.d("no loading in progress, starting new one");
            loadAccessTokenSubscription = loadAccessTokenObservable(code).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<AccessToken>() {

                        @Override
                        public void onError(Throwable throwable) {
                            Timber.e("accessTokenSubject.onError");
                            accessTokenSubject.onError(throwable);
                        }

                        @Override
                        public void onNext(AccessToken data) {
                            Timber.d("accessTokenSubject.onNext: " + data);
                            // Caching accessToken data
                            accessToken = data;
                            accessTokenSubject.onNext(accessToken);
                        }
                    });
        }
        return accessTokenSubject.asObservable();
    }

    public Observable<AccessToken> loadAccessTokenObservable(String code) {
        if (accessToken == null) {
            return authApi.getAccessToken(code, metaData.getClientId(), metaData.getClientSecret(), new EmptyBody()).map(new Func1<AccessTokenRepresentation, AccessToken>() {
                @Override
                public AccessToken call(AccessTokenRepresentation accessTokenRepresentation) {
                    return accessTokenMapper.transform(accessTokenRepresentation);
                }
            });
        } else {
            return Observable.just(accessToken);
        }
    }
}