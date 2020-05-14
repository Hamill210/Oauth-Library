package com.codesquad.oauthgithublibrary.auth;

public class AuthCredential {

    String credential;

    public AuthCredential() {

    }

    public AuthCredential(String credential) {
        this.credential = credential;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    @Override
    public String toString() {
        return "AuthCredential{" +
                "credential='" + credential + '\'' +
                '}';
    }
}
