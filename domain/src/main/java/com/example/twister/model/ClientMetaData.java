package com.example.twister.model;

import java.util.Random;

public class ClientMetaData {
    // TODO refactor class
    private final String clientID;
    private final String stateValue;
    private final String clientSecret;
    private final String redirectURL;

    public ClientMetaData(String clientID, String clientSecret, String redirectURL) {
        this.clientID = clientID;
        // TODO validate
        this.stateValue = "app-state" + System.nanoTime() + "-" + new Random().nextInt();
        this.clientSecret = clientSecret;
        this.redirectURL = redirectURL;
    }

    public String getClientID() {
        return clientID;
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
