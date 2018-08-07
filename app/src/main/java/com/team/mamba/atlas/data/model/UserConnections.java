package com.team.mamba.atlas.data.model;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team.mamba.atlas.utils.formatData.AppFormatter;


@Keep
public class UserConnections {

    @SerializedName("id")
    @Expose
    public String id;


    @SerializedName("isOrgBus")
    @Expose
    public boolean isOrgBus;

    @SerializedName("requestingUserID")
    @Expose
    public String requestingUserID;

    @SerializedName("requestingUserName")
    @Expose
    public String requestingUserName;

    @SerializedName("consentingUserID")
    @Expose
    public String consentingUserID;

    @SerializedName("consentingUserName")
    @Expose
    public String consentingUserName;

    @SerializedName("connectionType")
    @Expose
    public int connectionType;

    @SerializedName("isConfirmed")
    @Expose
    public boolean isConfirmed;

    @SerializedName("isViewed")
    @Expose
    public boolean viewed;

    @SerializedName("isDirectory")
    @Expose
    public boolean directory;

    @SerializedName("timestamp")
    @Expose
    public double timestamp;

    public boolean needsApproval;
    public boolean recentActivity;


    public UserConnections(){


    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBusiness() {
        return isOrgBus;
    }

    public void setOrgBus(boolean orgBus) {
        this.isOrgBus = orgBus;
    }

    public String getRequestingUserID() {
        return requestingUserID;
    }

    public void setRequestingUserID(String requestingUserID) {
        this.requestingUserID = requestingUserID;
    }

    public String getRequestingUserName() {
        return requestingUserName;
    }

    public void setRequestingUserName(String requestingUserName) {
        this.requestingUserName = requestingUserName;
    }

    public String getConsentingUserID() {
        return consentingUserID;
    }

    public void setConsentingUserID(String consentingUserID) {
        this.consentingUserID = consentingUserID;
    }

    public String getConsentingUserName() {
        return consentingUserName;
    }

    public void setConsentingUserName(String consentingUserName) {
        this.consentingUserName = consentingUserName;
    }

    public int getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(int connectionType) {
        this.connectionType = connectionType;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.isConfirmed = confirmed;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public long getTimestamp() {

        String adjustedTime = AppFormatter.timeStampFormatter.format(timestamp);

        return Long.parseLong(adjustedTime);
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isNeedsApproval() {
        return needsApproval;
    }

    public void setNeedsApproval(boolean needsApproval) {
        this.needsApproval = needsApproval;
    }

    public boolean isRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(boolean recentActivity) {
        this.recentActivity = recentActivity;
    }
}
