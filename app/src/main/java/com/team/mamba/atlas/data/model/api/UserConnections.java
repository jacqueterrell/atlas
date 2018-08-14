package com.team.mamba.atlas.data.model.api;

import android.support.annotation.Keep;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team.mamba.atlas.utils.formatData.AppFormatter;


@Keep
public class UserConnections {

    @SerializedName("id")
    @Expose
    public String id= "";


    public boolean isOrgBus;
    public String requestingUserID= "";
    public String requestingUserName= "";
    public String consentingUserID= "";
    public String consentingUserName= "";
    public String conDeviceToken = "";
    public String reqDeviceToken = "";
    public int connectionType;
    public boolean isConfirmed;
    public boolean viewed;
    public boolean directory;
    public double timestamp;

    @Exclude private boolean needsApproval;
    @Exclude private boolean recentActivity;
    @Exclude private UserProfile  userProfile;
    @Exclude private BusinessProfile businessProfile;



    public UserConnections(){


    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOrgBus() {
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

    public void setReqDeviceToken(String reqDeviceToken) {
        this.reqDeviceToken = reqDeviceToken;
    }

    public String getReqDeviceToken() {
        return reqDeviceToken;
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

    public void setConDeviceToken(String conDeviceToken) {
        this.conDeviceToken = conDeviceToken;
    }

    public String getConDeviceToken() {
        return conDeviceToken;
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


   @Exclude public UserProfile getUserProfile() {
        return userProfile;
    }

    @Exclude public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Exclude public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    @Exclude public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }
}
