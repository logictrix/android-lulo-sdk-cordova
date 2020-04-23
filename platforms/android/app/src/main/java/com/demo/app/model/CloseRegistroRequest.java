package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseRegistroRequest {

    @SerializedName("Uid")
    @Expose
    public String Uid;

    @SerializedName("IdentificationNumber")
    @Expose
    public String IdentificationNumber;

    @SerializedName("IndividualFingerCode")
    @Expose
    public String IndividualFingerCode;


    //@SerializedName("returnImages")
    //@Expose
    //public String returnImages;

    /**
     * No args constructor for use in serialization
     *
     * @param uid
     * @param identificationNumber
     */
    public CloseRegistroRequest(String uid, String identificationNumber) {
    }

    /**
     *
     * @param uid
     */
    public CloseRegistroRequest(String uid, String IdentificationNumber, String IndividualCodeFinger) {
        super();
        this.Uid = uid;
        this.IdentificationNumber = IdentificationNumber;
        this.IndividualFingerCode = IndividualCodeFinger;
        //this.returnImages = "false";
    }



}
