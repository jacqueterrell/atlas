package com.team.mamba.atlas.data.model;

public class ConnectionRecord {

    private String name;

    private String userId;

    private boolean isBusiness;

    private boolean isNeedsApproval;

    private boolean isRecentActivity;

    private String timestamp;

    public ConnectionRecord(){

    }


    private ConnectionRecord(Builder builder){

        this.name = builder.name;
        this.isBusiness = builder.isBusiness;
        this.isNeedsApproval = builder.isNeedsApproval;
        this.isRecentActivity = builder.isRecentActivity;
        this.timestamp = builder.timestamp;
        this.userId = builder.userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public static class Builder{

        private String name;
        private boolean isBusiness;
        private boolean isNeedsApproval;
        private boolean isRecentActivity;
        private String timestamp;
        private String userId;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBusiness(boolean business) {
            isBusiness = business;
            return this;
        }

        public Builder setNeedsApproval(boolean needsApproval) {
            isNeedsApproval = needsApproval;
            return this;
        }

        public Builder setRecentActivity(boolean recentActivity) {
            isRecentActivity = recentActivity;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public ConnectionRecord build(){

            return new ConnectionRecord(this);
        }
    }
}
