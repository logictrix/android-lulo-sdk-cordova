package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogResponse {

    @SerializedName("Response")
    @Expose
    public int Response;


    public int getResponse() {
        return Response;
    }

}