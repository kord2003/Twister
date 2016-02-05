package com.example.twister.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.twister.R;
import com.example.twister.presenters.LoginPresenter;
import com.example.twister.view.LoginView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by sharlukovich on 02.02.2016.
 */
public class LoginActivity extends BaseActivity implements LoginView {
    public static final String REQUEST_CLIENT_CODE_URL = "https://home.nest.com/login/oauth2?client_id=%s&state=%s";

    // Views
    @Bind(R.id.wvLogin)
    protected WebView webView;

    // Presenters
    @Inject
    protected LoginPresenter loginPresenter;

    // Plain fields
    private String code;
    private com.example.twister.model.ClientMetaData clientMetaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializePresenters();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.attach(this);
        loginPresenter.loadClientMetaData();
    }

    private void initializePresenters() {
        getComponent().inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginPresenter.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void handleClientMetaData(com.example.twister.model.ClientMetaData clientMetaData) {
        this.clientMetaData = clientMetaData;
        showLoginPage();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showLoginPage() {
        webView.setWebViewClient(new RedirectClient());
        webView.getSettings().setJavaScriptEnabled(true);

        final String url = String.format(REQUEST_CLIENT_CODE_URL, clientMetaData.getClientID(), clientMetaData.getStateValue());
        Timber.d("url: " + url);
        webView.loadUrl(url);
    }

    @Override
    public void handleAccessTokenLoaded(com.example.twister.model.AccessToken accessToken) {
        Timber.d("handleAccessTokenLoaded");
        Intent intent = new Intent(this, NestOverviewActivity.class);
        startActivity(intent);
        finish();
    }

    private class RedirectClient extends WebViewClient {
        private String pendingUrl;

        private boolean processRedirect(String url) {
            Timber.d("redirect url: " + url);
            if (!url.startsWith(clientMetaData.getRedirectURL())) {
                code = null;
                return false;
            }
            code = parseCode(url);
            Timber.d("code: " + code);
            if (TextUtils.isEmpty(code)) {
                return true;
            }
            loginPresenter.loadAccessToken(code);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // shouldOverrideUrlLoading() not working on KitKat :(
            Timber.d("RedirectClient onPageStarted " + url);
            if (pendingUrl == null) {
                pendingUrl = url;
            } else if (!url.equals(pendingUrl)) {
                Timber.d("RedirectClient Detected HTTP redirect " + pendingUrl + "->" + url);
                pendingUrl = null;
                processRedirect(url);
            }
        }
    }

    private static String parseCode(String url) {
        try {
            return Uri.parse(url).getQueryParameter("code");
        } catch (Exception e) {
            return null;
        }
    }
}