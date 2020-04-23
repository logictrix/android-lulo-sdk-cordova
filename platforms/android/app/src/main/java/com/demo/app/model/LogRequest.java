package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogRequest {

    @SerializedName("ExceptionType")
    @Expose
    public String ExceptionType;

    @SerializedName("Source")
    @Expose
    public String Source;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("Device")
    @Expose
    public String Device;

    @SerializedName("OperatingSystem")
    @Expose
    public String OperatingSystem;


    @SerializedName("Project")
    @Expose
    public String Project;

    @SerializedName("Uid")
    @Expose
    public String Uid;

    @SerializedName("UuidDevice")
    @Expose
    public String UuidDevice;

    @SerializedName("Attempt")
    @Expose
    public String Attempt;
    /**
     * /**
     * No args constructor for use in serialization
     */
    public LogRequest() {
    }


    public LogRequest(String ExceptionType, String Source, String Message, String Device, String OperatingSystem, String Project, String Uid, String UuidDevice, String Attempt ) {
        super();
        this.ExceptionType = ExceptionType;
        this.Source = Source;
        this.Message = Message;
        this.Device = Device;
        this.OperatingSystem = OperatingSystem;
        this.Project = Project;
        this.Uid = Uid;
        this.UuidDevice = UuidDevice;
        this.Attempt = Attempt;
    }
}