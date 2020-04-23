package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceTransactionInfo {

    @SerializedName("ProductId")
    @Expose
    public String productId;
    @SerializedName("CustomerPhoto")
    @Expose
    public String customerPhoto;

    /**
     * No args constructor for use in serialization
     *
     */
    public FaceTransactionInfo() {
    }

    /**
     *
     * @param customerPhoto
     * @param productId
     */
    public FaceTransactionInfo(String productId, String customerPhoto) {
        this.productId = productId;
        this.customerPhoto = customerPhoto;
    }

}
