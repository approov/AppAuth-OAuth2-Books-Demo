package com.criticalblue.auth.demo.auth;

import android.content.Intent;

public interface AuthRepoListener {
    default void onServiceDiscoveryStart(AuthRepo repo) {}
    default void onServiceDiscoveryComplete(AuthRepo repo) {}
    default void onConfigSuccess(AuthRepo repo) {}
    default void onConfigFailure(AuthRepo repo, AuthException ex) {}
    default void onUserAuthStart(AuthRepo repo) {}
            void onUserAgentRequest(AuthRepo repo, Intent intent);
    default void onUserAuthComplete(AuthRepo repo) {}
    default void onCodeExchangeStart(AuthRepo repo) {}
    default void onCodeExchangeComplete(AuthRepo repo) {}
    default void onLoginSuccess(AuthRepo repo) {}
    default void onLoginFailure(AuthRepo repo, AuthException ex) {}
    default void onLogoutSuccess(AuthRepo repo) {}
}
