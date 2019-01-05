package com.example.adolphe.booksapp.Model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoBook {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("selfLink")
    @Expose
    private String selfLink;
    @SerializedName("volumeInfo")
    @Expose
    private VolumeInfo volumeInfo;
    @SerializedName("saleInfo")
    @Expose
    private SaleInfo saleInfo;
    @SerializedName("accessInfo")
    @Expose
    private AccessInfo accessInfo;
    @SerializedName("searchInfo")
    @Expose
    private SearchInfo searchInfo;

    private Bitmap imageBitmap;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getPriceAsString() {
        String b = "FREE";
        if(getSaleInfo().getRetailPrice() != null ) {
            if(getSaleInfo().getRetailPrice().getAmount() != null && getSaleInfo().getRetailPrice().getCurrencyCode() != null){
               b = getSaleInfo().getRetailPrice().getCurrencyCode() + " " + getSaleInfo().getRetailPrice().getAmount();
            }
        } else if (getSaleInfo().getListPrice() != null) {
            if(getSaleInfo().getListPrice().getAmount() != null && getSaleInfo().getListPrice().getCurrencyCode() != null){
               b = getSaleInfo().getListPrice().getCurrencyCode() + " " + getSaleInfo().getRetailPrice().getAmount();
            }
        }
        return b;
    }
}
