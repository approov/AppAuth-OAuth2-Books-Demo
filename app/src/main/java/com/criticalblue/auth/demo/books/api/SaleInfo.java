
package com.criticalblue.auth.demo.books.api;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaleInfo {

    @SerializedName("buyLink")
    private String mBuyLink;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("isEbook")
    private Boolean mIsEbook;
    @SerializedName("listPrice")
    private ListPrice mListPrice;
    @SerializedName("offers")
    private List<Offer> mOffers;
    @SerializedName("retailPrice")
    private RetailPrice mRetailPrice;
    @SerializedName("saleability")
    private String mSaleability;

    public String getBuyLink() {
        return mBuyLink;
    }

    public void setBuyLink(String buyLink) {
        mBuyLink = buyLink;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Boolean getIsEbook() {
        return mIsEbook;
    }

    public void setIsEbook(Boolean isEbook) {
        mIsEbook = isEbook;
    }

    public ListPrice getListPrice() {
        return mListPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        mListPrice = listPrice;
    }

    public List<Offer> getOffers() {
        return mOffers;
    }

    public void setOffers(List<Offer> offers) {
        mOffers = offers;
    }

    public RetailPrice getRetailPrice() {
        return mRetailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        mRetailPrice = retailPrice;
    }

    public String getSaleability() {
        return mSaleability;
    }

    public void setSaleability(String saleability) {
        mSaleability = saleability;
    }

}
