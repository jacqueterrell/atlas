package com.team.mamba.atlas.data.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {

    private String id;
    private String deviceToken;
    private String code;
    private int score;
    private String firstName;
    private String lastName;
    private String email = "...";
    private String workEmail = "...";
    private String phone = "...";
    private String workPhone = "...";
    private String homePhone = "...";
    private String personalPhone = "...";
    private String fax = "...";
    private String street;
    private String cityStateZip = "...";
    private Map<String,String> workHistory;
    private Map<String,String> education;
    private String currentEmployer;
    private String currentPosition;
    private String workStreet;
    private String workCityStateZip;
    private String imageUrl;
    private Map<String,String> connections;
    private int connectionsCount;
    private double timeStamp;
    private double dob;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityStateZip() {
        return cityStateZip;
    }

    public void setCityStateZip(String cityStateZip) {
        this.cityStateZip = cityStateZip;
    }

    public Map<String, String> getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(Map<String, String> workHistory) {
        this.workHistory = workHistory;
    }

    public Map<String, String> getEducation() {
        return education;
    }

    public void setEducation(Map<String, String> education) {
        this.education = education;
    }

    public String getCurrentEmployer() {
        return currentEmployer;
    }

    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getWorkStreet() {
        return workStreet;
    }

    public void setWorkStreet(String workStreet) {
        this.workStreet = workStreet;
    }

    public String getWorkCityStateZip() {
        return workCityStateZip;
    }

    public void setWorkCityStateZip(String workCityStateZip) {
        this.workCityStateZip = workCityStateZip;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String,String> getConnections() {
        return connections;
    }

    public void setConnections(Map<String,String> connections) {
        this.connections = connections;
    }

    public int getConnectionsCount() {
        return connectionsCount;
    }

    public void setConnectionsCount(int connectionsCount) {
        this.connectionsCount = connectionsCount;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getDob() {
        return dob;
    }

    public void setDob(double dob) {
        this.dob = dob;
    }

    public UserProfile(){

    }


    private static class Builder {

        private String cellPhone = "...";
        private String officePhone = "...";
        private String homePhone = "...";
        private String personalPhone = "...";
        private String fax = "...";
        private String personalEmail = "...";
        private String workEmail = "...";
        private String homeAddress = "...";
        private String workAddress = "...";
        private List<String> workHistory = new ArrayList<>();
        private List<String> education = new ArrayList<>();

        public Builder setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
            return this;
        }

        public Builder setOfficePhone(String officePhone) {
            this.officePhone = officePhone;
            return this;
        }

        public Builder setHomePhone(String homePhone) {
            this.homePhone = homePhone;
            return this;
        }

        public Builder setPersonalPhone(String personalPhone) {
            this.personalPhone = personalPhone;
            return this;
        }

        public Builder setFax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder setPersonalEmail(String personalEmail) {
            this.personalEmail = personalEmail;
            return this;
        }

        public Builder setWorkEmail(String workEmail) {
            this.workEmail = workEmail;
            return this;
        }

        public Builder setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
            return this;
        }

        public Builder setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
            return this;
        }

        public Builder setWorkHistory(List<String> workHistory) {
            this.workHistory = workHistory;
            return this;
        }

        public Builder setEducation(List<String> education) {
            this.education = education;
            return this;
        }
    }
}
