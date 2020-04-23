package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerificationIdentityRequest {

    @SerializedName("DocumentType")
    @Expose
    public String documentType;

    @SerializedName("IdentificationNumber")
    @Expose
    public String identificationNumber;

    @SerializedName("Face")
    @Expose
    public String Face;

    @SerializedName("FingerPrint")
    @Expose
    public String FingerPrint;

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
    public VerificationIdentityRequest() {
    }

    /**
     *
     * @param documentType
     * @param identificationNumber
     * @param Face
     * @param FingerPrint
     */
    public VerificationIdentityRequest(String documentType, String identificationNumber, String Face, String FingerPrint, String latitude, String longitude) {
        this.documentType = documentType;
        this.identificationNumber = identificationNumber;
        this.Face = Face;
        this.FingerPrint = FingerPrint;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
}
