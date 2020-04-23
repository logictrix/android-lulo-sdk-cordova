package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetConfigResponse {


    @SerializedName("TryLiveness")
    @Expose
    private Integer TryLiveness;

    @SerializedName("Token_KYC")
    @Expose
    private String Token_KYC;

    @SerializedName("UrlServiceLiveness")
    @Expose
    private String UrlServiceLiveness;

    @SerializedName("TypeLiveness")
    @Expose
    private Integer TypeLiveness;

    @SerializedName("Token")
    @Expose
    private String Token;

    @SerializedName("ProjectName")
    @Expose
    private String ProjectName_Server;

    @SerializedName("ApiKey")
    @Expose
    private String ApiKey_Server;


    @SerializedName("Base_Uri")
    @Expose
    private String Base_Url;

    //@SerializedName("ActivityFlow")
    //@Expose
    //private ActivityFlow activityFlow;

    //public class ActivityFlow {

      //  @SerializedName("WelcomeView")
        //@Expose
        //private String welcomeView;

        //@SerializedName("DocView")
        //@Expose
        //private String docView;

        //@SerializedName("FaceView")
        //@Expose
        //private String faceView;

        //@SerializedName("FrontView")
        //@Expose
        //private String FrontView;

        //@SerializedName("BackView")
        //@Expose
        //private String BackView;

        //public String getWelcomeView() {
            //return welcomeView;
        //}

        //public String getDocView() {
            //return docView;
        //}

        //public String getFaceView() {
            //return faceView;
        //}

        //public String getFrontView() {
            //return FrontView;
        //}
        //public String BackView() {
            //return BackView;
        //}
    //}

    @SerializedName("Uid")
    @Expose
    private String Uid;


    public Integer getTryLiveness() { return TryLiveness;}
    public String getToken_KYC() { return Token_KYC;}

    public String getUrlServiceLiveness() {
        return UrlServiceLiveness;
    }

    public Integer getTypeLiveness() { return TypeLiveness;}
    public String getToken() { return Token;}
    public String getUid() { return Uid;}
    public String getBase_Url() {
        return Base_Url;
    }
    public String getApiKey_Server() {
        return ApiKey_Server;
    }
    public String getProjectName_Server() {
        return ProjectName_Server;
    }
    //public ActivityFlow getActivityFlow () {return activityFlow;}
}
