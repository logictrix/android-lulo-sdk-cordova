package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAuthenticationResponse {

    @SerializedName("IdNumber")
    @Expose
    private String idNumber;

    @SerializedName("BranchName")
    @Expose
    private String branchName;

    @SerializedName("UserName")
    @Expose
    private String userName;

    @SerializedName("Names")
    @Expose
    private String names;

    @SerializedName("Surnames")
    @Expose
    private String surnames;

    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;

    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("CreationDate")
    @Expose
    private String creationDate;

    @SerializedName("State")
    @Expose
    private String state;

    @SerializedName("Locked")
    @Expose
    private String locked;

    @SerializedName("AuthType")
    @Expose
    private String authType;

    public String getState() { return state;}
    /**
     * No args constructor for use in serialization
     *
     */
    public UserAuthenticationResponse() {
    }

    /**
     *
     */
    public UserAuthenticationResponse(String idNumber, String branchName, String userName, String names, String surnames, String emailAddress, String phoneNumber, String creationDate, String state, String locked, String authType) {
        super();
        this.idNumber = idNumber;
        this.branchName = branchName;
        this.userName = userName;
        this.names = names;
        this.surnames = surnames;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.state = state;
        this.locked = locked;
        this.authType = authType;
    }
}
