package com.example.vishnu.typroject;

import com.google.firebase.database.Exclude;

public class UploadImages {

    private String mName;
    private String mImageUrl;
    private String mKey;

    UploadImages() {
        //Empty Constructor Needed
    }

    UploadImages(String mName, String mImageUrl) {

        this.mImageUrl = mImageUrl;
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
