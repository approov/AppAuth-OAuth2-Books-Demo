package com.criticalblue.auth.demo.auth;

public enum AuthEvent {
    AUTH_LOGIN_START("Starting login"),
    AUTH_SERVICE_DISCOVERY_START("Discovering service configuration"),
    AUTH_SERVICE_DISCOVERY_FINISH("Service discovery finished"),
    AUTH_USER_AUTH_START("Requesting user authorization"),
    AUTH_USER_AUTH_FINISH("User authorization finished"),
    AUTH_CODE_EXCHANGE_START("Exchanging code for access token"),
    AUTH_CODE_EXCHANGE_FINISH("Code exchange finished"),
    AUTH_USER_INFO_START("Gathering user information"),
    AUTH_USER_INFO_FINISH("User information gathering finished"),
    AUTH_LOGIN_SUCCESS("Login succeeded"),
    AUTH_LOGIN_FAILURE("Login failed"),
    AUTH_LOGOUT_START("Logout succeeded"),
    AUTH_LOGOUT_SUCCESS("Logout succeeded"),
    AUTH_LOGOUT_FAILURE("Logout failed");

    private String description;

    private AuthEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
