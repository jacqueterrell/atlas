package com.team.mamba.atlas.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team.mamba.atlas.utils.formatData.AppFormatter;

public class ConnectionRecord {


    private String name;

    private String userId;

    @SerializedName("isOrgBus")
    @Expose
    private boolean isBusiness;

    private String requestingUserID;
    private String requestingUserName;
    private String consentingUserID;
    private String consentingUserName;
    private int connectionType;
    private boolean isConfirmed;
    private boolean isViewed;
    private boolean isDirectory;
    private double timeStamp;

    private boolean isNeedsApproval;
    private boolean isRecentActivity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
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
        isConfirmed = confirmed;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {

        String adjustedTimeStamp = AppFormatter.timeStampFormatter.format(timeStamp);//converts IOS timestamp over to normal format
        this.timeStamp = Double.parseDouble(adjustedTimeStamp);
    }

    public boolean isNeedsApproval() {
        return isNeedsApproval;
    }

    public void setNeedsApproval(boolean needsApproval) {
        isNeedsApproval = needsApproval;
    }

    public boolean isRecentActivity() {
        return isRecentActivity;
    }

    public void setRecentActivity(boolean recentActivity) {
        isRecentActivity = recentActivity;
    }

}
