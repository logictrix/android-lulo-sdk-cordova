package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("error1")
    @Expose
    public String error1;

    @SerializedName("error2")
    @Expose
    public String error2;

    public String getError1() {
        return error1;
    }

    public String getError2() {
        return error2;
    }
}
