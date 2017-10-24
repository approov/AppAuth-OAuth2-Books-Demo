package com.criticalblue.auth.demo.auth;


interface UserInfoCallback {
    void call(UserInfo userInfo, AuthException ex);
}
