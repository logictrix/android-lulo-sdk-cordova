package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAuthenticationRequest {

    @SerializedName("UserName")
    @Expose
    public String UserName;

    @SerializedName("PassWord")
    @Expose
    public String PassWord;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserAuthenticationRequest() {
    }

    /**
     *
     */
    public UserAuthenticationRequest(String userName, String passWord) {
        super();
        this.UserName = userName;
        this.PassWord = passWord;
    }
}
