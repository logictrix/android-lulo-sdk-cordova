package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetConfigRequest {

    @SerializedName("ProjectName")
    @Expose
    public String ProjectName;

    @SerializedName("ApiKey")
    @Expose
    public String ApiKey;

    @SerializedName("Message")
    @Expose
    public String Message;
    /**
     * No args constructor for use in serialization
     *
     * @param message
     */
    public GetConfigRequest(String message) {
        this.Message = message;
    }


}
