
package com.criticalblue.auth.demo.books.api;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class BookShelfResult {

    @SerializedName("access")
    private String mAccess;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("id")
    private String mId;
    @SerializedName("kind")
    private String mKind;
    @SerializedName("selfLink")
    private String mSelfLink;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated")
    private String mUpdated;
    @SerializedName("volumeCount")
    private String mVolumeCount;
    @SerializedName("volumesLastUpdated")
    private String mVolumesLastUpdated;

    public String getAccess() {
        return mAccess;
    }

    public void setAccess(String access) {
        mAccess = access;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getSelfLink() {
        return mSelfLink;
    }

    public void setSelfLink(String selfLink) {
        mSelfLink = selfLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public String getVolumeCount() {
        return mVolumeCount;
    }

    public void setVolumeCount(String volumeCount) {
        mVolumeCount = volumeCount;
    }

    public String getVolumesLastUpdated() {
        return mVolumesLastUpdated;
    }

    public void setVolumesLastUpdated(String volumesLastUpdated) {
        mVolumesLastUpdated = volumesLastUpdated;
    }

}
