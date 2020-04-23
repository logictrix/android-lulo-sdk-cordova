package com.demo.app.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocRequestFront {
    @SerializedName("Image")
    @Expose
    public String image;

    @SerializedName("Uid")
    @Expose
    public String uid;

    @SerializedName("DocumentType")
    @Expose
    public String DocumentType;


    /**
     * No args constructor for use in serialization
     *
     */
    public DocRequestFront() {
    }

    /**
     *
     * @param uid
     * @param image
     */
    public DocRequestFront(String image, String uid, String documentType) {
        super();
        this.image = image;
        this.uid = uid;
        this.DocumentType = documentType;
    }
}
