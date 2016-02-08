package com.example.twister.model;

import java.util.Random;

public class MetaData {
    private final String clientId;
    private final String stateValue;
    private final String clientSecret;
    private final String redirectURL;

    public MetaData(String clientId, String clientSecret, String redirectURL) {
        this.clientId = clientId;
        this.stateValue = "state-" + System.nanoTime() + "-" + new Random().nextInt();
        this.clientSecret = clientSecret;
        this.redirectURL = redirectURL;
    }

    public String getClientId() {
        return clientId;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectURL() {
        return redirectURL;
    }
}