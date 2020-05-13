package com.codesquad.oauthgithublibrary.auth;

public class AuthResponse {

    boolean authorizationSuccessful;

    public AuthResponse() {
        
    }

    public AuthResponse(boolean authorizationSuccessful) {
        this.authorizationSuccessful = authorizationSuccessful;
    }

    public boolean isAuthorizationSuccessful() {
        return authorizationSuccessful;
    }

    public void setAuthorizationSuccessful(boolean authorizationSuccessful) {
        this.authorizationSuccessful = authorizationSuccessful;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "authorizationSuccessful=" + authorizationSuccessful +
                '}';
    }
}
