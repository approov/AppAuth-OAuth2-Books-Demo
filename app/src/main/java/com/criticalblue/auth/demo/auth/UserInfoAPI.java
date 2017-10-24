package com.criticalblue.auth.demo.auth;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserInfoAPI {

    @GET("./")
    public Call<UserInfoResult> getUserInfo();
}
