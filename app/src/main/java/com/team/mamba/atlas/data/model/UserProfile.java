package com.team.mamba.atlas.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team.mamba.atlas.utils.formatData.AppFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {
    @SerializedName("id")
    private String id;

    @SerializedName("deviceToken")
    private String deviceToken = "...";

    @SerializedName("code")
    @Expose
    private String code= "...";

    @SerializedName("score")
    @Expose
    private int score;

    @SerializedName("firstName")
    @Expose
    private String firstName= "...";

    @SerializedName("lastName")
    @Expose
    private String lastName= "...";

    @SerializedName("email")
    @Expose
    private String email= "...";

    @SerializedName("workEmail")
    @Expose
    private String workEmail= "...";

    @SerializedName("homePhone")
    @Expose
    private String homePhone= "...";

    @SerializedName("personalPhone")
    @Expose
    private String personalPhone= "...";

    @SerializedName("workPhone")
    @Expose
    private String workPhone= "...";

    @SerializedName("fax")
    @Expose
    private String fax= "...";

    @SerializedName("street")
    @Expose
    private String street= "...";

    @SerializedName("cityStateZip")
    @Expose
    private String cityStateZip= "...";

    @SerializedName("workHistory")
    @Expose
    private List<Map<String,String>> workHistory = new ArrayList<>();

    @SerializedName("education")
    @Expose
    private List<Map<String,String>> education = new ArrayList<>();

    @SerializedName("currentEmployer")
    @Expose
    private String currentEmployer= "...";

    @SerializedName("currentPosition")
    @Expose
    private String currentPosition= "...";

    @SerializedName("workStreet")
    @Expose
    private String workStreet= "...";

    @SerializedName("workCityStateZip")
    @Expose
    private String workCityStateZip= "...";

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl= "...";


    @SerializedName("connections")
    @Expose
    private Map<String,String> connections = new LinkedHashMap<>();

    @SerializedName("connectionsCount")
    @Expose
    private int connectionsCount;

    @SerializedName("timestamp")
    @Expose
    private double timeStamp;

    @SerializedName("dob")
    @Expose
    private double dob;

    @SerializedName("phone")
    @Expose
    private String phone= "...";


    public UserProfile() {


    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

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

    public List<Map<String,String>> getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(List<Map<String,String>> workHistory) {
        this.workHistory = workHistory;
    }

    public List<Map<String,String>> getEducation() {
        return education;
    }

    public void setEducation(List<Map<String,String>> education) {
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

    public long getTimeStamp() {

        String adjustedTime = AppFormatter.timeStampFormatter.format(timeStamp);

        return Long.parseLong(adjustedTime);
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getDob() {

        String adjustedTime = AppFormatter.timeStampFormatter.format(timeStamp);

        return Long.parseLong(adjustedTime);
    }

    public void setDob(double dob) {
        this.dob = dob;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }
}
