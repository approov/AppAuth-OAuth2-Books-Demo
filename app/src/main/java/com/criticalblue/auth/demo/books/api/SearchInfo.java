
package com.criticalblue.auth.demo.books.api;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchInfo {

    @SerializedName("textSnippet")
    private String mTextSnippet;

    public String getTextSnippet() {
        return mTextSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        mTextSnippet = textSnippet;
    }

}
