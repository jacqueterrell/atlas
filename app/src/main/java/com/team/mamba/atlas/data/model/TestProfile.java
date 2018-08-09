package com.team.mamba.atlas.data.model;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TestProfile {

   @SerializedName("isConfirmed")
    @Expose
    public boolean isConfirmed;

    @SerializedName("requestingUserID")
    @Expose
    private String requestingUserID;

    @SerializedName("timestamp")
    @Expose
    private double timestamp;


    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getRequestingUserID() {
        return requestingUserID;
    }

    public double getTimestamp() {
        return timestamp;
    }
}
