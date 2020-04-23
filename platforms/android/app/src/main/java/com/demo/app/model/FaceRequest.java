package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceRequest {

    @SerializedName("ProductId")
    @Expose
    public String productId;
    @SerializedName("CustomerPhoto")
    @Expose
    public String customerPhoto;

    @SerializedName("Latitude")
    @Expose
    public String Latitude;

    @SerializedName("Longitude")
    @Expose
    public String Longitude;
    /**
     * No args constructor for use in serialization
     *
     */
    public FaceRequest() {
    }

    /**
     *
     * @param customerPhoto
     * @param productId
     */
    public FaceRequest(String productId, String customerPhoto, String latitude, String longitude) {
        this.productId = productId;
        this.customerPhoto = customerPhoto;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
}
