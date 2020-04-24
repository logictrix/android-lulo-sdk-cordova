package com.demo.app.liveness.dialogs.resultsdialogs;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class SNDisplayableResult implements Parcelable {
    private String title;
    private String value;
    private Bitmap image;

    public SNDisplayableResult(String title, String value) {
        this.title = title;
        this.value = value;
    }

    //Copy constructor
    public SNDisplayableResult(SNDisplayableResult data) {
        this.title = data.title;
        this.value = data.value;
        this.image = data.image;
    }

    protected SNDisplayableResult(Parcel in) {
        title = in.readString();
        value = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(value);
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SNDisplayableResult> CREATOR = new Creator<SNDisplayableResult>() {
        @Override
        public SNDisplayableResult createFromParcel(Parcel in) {
            return new SNDisplayableResult(in);
        }

        @Override
        public SNDisplayableResult[] newArray(int size) {
            return new SNDisplayableResult[size];
        }
    };

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public Bitmap getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Key = " + title + "\n" +
                "Value = " + value + "\n\n";
    }
}
