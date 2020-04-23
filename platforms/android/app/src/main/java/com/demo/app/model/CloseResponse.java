package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class CloseResponse extends ResponseBody {

    public CloseResponse() {
    }

    @SerializedName("Uid")
    @Expose
    private String uid;
    @SerializedName("StartingDate")
    @Expose
    private String startingDate;
    @SerializedName("CreationDate")
    @Expose
    private String creationDate;
    @SerializedName("CreationIP")
    @Expose
    private String creationIP;
    @SerializedName("IdNumber")
    @Expose
    private String idNumber;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("SecondName")
    @Expose
    private String secondName;
    @SerializedName("FirstSurname")
    @Expose
    private String firstSurname;
    @SerializedName("SecondSurname")
    @Expose
    private String secondSurname;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("BarcodeText")
    @Expose
    private String barcodeText;
    @SerializedName("OcrTextSideOne")
    @Expose
    private String ocrTextSideOne;
    @SerializedName("OcrTextSideTwo")
    @Expose
    private String ocrTextSideTwo;
    @SerializedName("SideOneWrongAttempts")
    @Expose
    private Integer sideOneWrongAttempts;
    @SerializedName("SideTwoWrongAttempts")
    @Expose
    private Integer sideTwoWrongAttempts;
    @SerializedName("FoundOnAdoAlert")
    @Expose
    private Boolean foundOnAdoAlert;
    @SerializedName("adoProjectId")
    @Expose
    private String adoProjectId;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("ProductId")
    @Expose
    private String productId;

    @SerializedName("DactilarCode")
    @Expose
    private String dactilarCode;

    @SerializedName("TransactionType")
    @Expose
    private String transactionType;

    @SerializedName("TransactionTypeName")
    @Expose
    private String transactionTypeName;

    @SerializedName("Extras")
    @Expose
    private Extras extras;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public String getUid() {
        return uid;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCreationIP() {
        return creationIP;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBarcodeText() {
        return barcodeText;
    }

    public String getOcrTextSideOne() {
        return ocrTextSideOne;
    }

    public String getOcrTextSideTwo() {
        return ocrTextSideTwo;
    }

    public Integer getSideOneWrongAttempts() {
        return sideOneWrongAttempts;
    }

    public Integer getSideTwoWrongAttempts() {
        return sideTwoWrongAttempts;
    }

    public Boolean getFoundOnAdoAlert() {
        return foundOnAdoAlert;
    }

    public String getAdoProjectId() {
        return adoProjectId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public String getDactilarCode() {
        return dactilarCode;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }


    public static class Extras {

        public Extras() {
        }

        @SerializedName("additionalProp1")
        @Expose
        private String additionalProp1;

        @SerializedName("additionalProp2")
        @Expose
        private String additionalProp2;

        @SerializedName("additionalProp3")
        @Expose
        private String additionalProp3;

        @SerializedName("StateName")
        @Expose
        private String StateName;

        @SerializedName("IdState")
        @Expose
        private String IdState;

        public String getAdditionalProp1() {
            return additionalProp1;
        }

        public String getAdditionalProp2() {
            return additionalProp2;
        }

        public String getAdditionalProp3() {
            return additionalProp3;
        }

        public String getIdState() {
            return IdState;
        }

        public String getStateName() {
            return StateName;
        }

        public void setAdditionalProp1(String additionalProp1) {
            this.additionalProp1 = additionalProp1;
        }

        public void setAdditionalProp2(String additionalProp2) {
            this.additionalProp2 = additionalProp2;
        }

        public void setAdditionalProp3(String additionalProp3) {
            this.additionalProp3 = additionalProp3;
        }

        public void setStateName(String stateName) {
            StateName = stateName;
        }

        public void setIdState(String idState) {
            IdState = idState;
        }
    }
}
