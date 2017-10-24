
package com.criticalblue.auth.demo.auth;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UserInfoResult {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("email_verified")
    private Boolean mEmailVerified;
    @SerializedName("family_name")
    private String mFamilyName;
    @SerializedName("given_name")
    private String mGivenName;
    @SerializedName("hd")
    private String mHd;
    @SerializedName("locale")
    private String mLocale;
    @SerializedName("name")
    private String mName;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("sub")
    private String mSub;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Boolean getEmailVerified() {
        return mEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        mEmailVerified = emailVerified;
    }

    public String getFamilyName() {
        return mFamilyName;
    }

    public void setFamilyName(String familyName) {
        mFamilyName = familyName;
    }

    public String getGivenName() {
        return mGivenName;
    }

    public void setGivenName(String givenName) {
        mGivenName = givenName;
    }

    public String getHd() {
        return mHd;
    }

    public void setHd(String hd) {
        mHd = hd;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        mLocale = locale;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getSub() {
        return mSub;
    }

    public void setSub(String sub) {
        mSub = sub;
    }

}
