package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionInfo {

    @SerializedName("Image")
    @Expose
    public String image;
    @SerializedName("Uid")
    @Expose
    public String uid;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionInfo() {
    }

    /**
     *
     * @param uid
     * @param image
     */
    public TransactionInfo(String image, String uid) {
        super();
        this.image = image;
        this.uid = uid;
    }
}