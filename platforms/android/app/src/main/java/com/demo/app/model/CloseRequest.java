package com.demo.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseRequest {

    @SerializedName("Uid")
    @Expose
    public String uid;
    //@SerializedName("returnImages")
    //@Expose
    //public String returnImages;

    /**
     * No args constructor for use in serialization
     *
     */
    public CloseRequest() {
    }

    /**
     *
     * @param uid
     */
    public CloseRequest(String uid) {
        super();
        this.uid = uid;
        //this.returnImages = "false";
    }
}
