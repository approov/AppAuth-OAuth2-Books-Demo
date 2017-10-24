
package com.criticalblue.auth.demo.books.api;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ImageLinks {

    @SerializedName("smallThumbnail")
    private String mSmallThumbnail;
    @SerializedName("thumbnail")
    private String mThumbnail;

    public String getSmallThumbnail() {
        return mSmallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        mSmallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

}
