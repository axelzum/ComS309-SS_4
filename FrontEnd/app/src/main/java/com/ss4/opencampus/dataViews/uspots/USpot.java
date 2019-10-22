package com.ss4.opencampus.dataViews.uspots;

import android.graphics.BitmapFactory;

import android.graphics.Bitmap;

/**
 * @Author: Morgan Smith
 * USpot class to store USpot object data
 * Getter and setter methods for USpot properties
 * Conversion of Rating, Lat, and Long to string type.
 **/

public class USpot {

    public int usID;

    public String usName;

    public Double usRating;

    public Double usLatit;

    public Double usLongit;

    public String usCategory;

    private byte[] picBytes;

    public USpot() {
    }

    public USpot(int usID, String usName, Double usRating, Double usLatit, Double usLongit, String usCategory, byte[] picBytes) {
        this.usID = usID;
        this.usName = usName;
        this.usRating = usRating;
        this.usLatit = usLatit;
        this.usLongit = usLongit;
        this.usCategory = usCategory;
        this.picBytes = picBytes;
    }

    public int getUsID() { return usID; }

    public String getUsName() {
        return usName;
    }

    public Double getUsRating() {
        return usRating;
    }

    public Double getUsLatit() {
        return usLatit;
    }

    public Double getUsLongit() {
        return usLongit;
    }

    public String getUsCategory() {
        return usCategory;
    }

    public byte[] getPicBytes() {
        return picBytes;
    }

    //Converts Double latitude to string as a getter method
    public String getLatString() {
        return usLatit.toString();
    }

    //Converts Double longitude to string as a getter method
    public String getLongString() {
        return usLongit.toString();
    }

    public String getRatingString() {
        return usRating.toString();
    }

    public void setUsID(int usID) {
        this.usID = usID;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public void setUsRating(Double usRating) {
        this.usRating = usRating;
    }

    public void setUsLatit(Double usLatit) {
        this.usLatit = usLatit;
    }

    public void setUsLongit(Double usLongit) {
        this.usLongit = usLongit;
    }

    public void setUspotCategory(String uspotCategory) {
        this.usCategory = uspotCategory;
    }

    public void setPicBytes(byte[] picBytes) {
        this.picBytes = picBytes;
    }
    public Bitmap setBitmap() {
        return BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
    }

}

