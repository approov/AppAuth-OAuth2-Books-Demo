
package com.criticalblue.auth.demo.books.api;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BookListResult {

    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("kind")
    private String mKind;
    @SerializedName("totalItems")
    private Long mTotalItems;

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public Long getTotalItems() {
        return mTotalItems;
    }

    public void setTotalItems(Long totalItems) {
        mTotalItems = totalItems;
    }

}
