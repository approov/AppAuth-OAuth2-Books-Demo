package com.criticalblue.auth.demo.auth;

import android.content.Intent;

public interface AuthLoginListener {
    default void onStart(AuthRepo repo, AuthEvent event) {}
    default void onEvent(AuthRepo repo, AuthEvent event) {}
            void onUserAgentRequest(AuthRepo repo, Intent intent);
    default void onSuccess(AuthRepo repo, AuthEvent event) {}
    default void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {}
}
