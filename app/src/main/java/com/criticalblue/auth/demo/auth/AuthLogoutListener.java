package com.criticalblue.auth.demo.auth;

import android.content.Intent;

public interface AuthLogoutListener {
    default void onStart(AuthRepo repo, AuthEvent event) {}
    default void onSuccess(AuthRepo repo, AuthEvent event) {}
    default void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {}
}
