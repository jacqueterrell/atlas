package com.team.mamba.atlas.data.model.api;

import android.graphics.Typeface;
import android.support.annotation.Keep;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team.mamba.atlas.utils.formatData.AppFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@IgnoreExtraProperties
public class UserProfile {


    @SerializedName("id")
    public String id;

    @SerializedName("deviceToken")
    public String deviceToken = "...";

    @SerializedName("code")
    @Expose
    public String code= "...";

    @SerializedName("score")
    @Expose
    public int score;

    @SerializedName("firstName")
    @Expose
    public String firstName= "...";

    @SerializedName("lastName")
    @Expose
    public String lastName= "...";

    @SerializedName("email")
    @Expose
    public String email= "...";

    @SerializedName("workEmail")
    @Expose
    public String workEmail= "...";

    @SerializedName("homePhone")
    @Expose
    public String homePhone= "...";

    @SerializedName("personalPhone")
    @Expose
    public String personalPhone= "...";

    @SerializedName("workPhone")
    @Expose
    public String workPhone= "...";

    @SerializedName("fax")
    @Expose
    public String fax= "...";

    @SerializedName("street")
    @Expose
    public String street= "...";

    @SerializedName("cityStateZip")
    @Expose
    public String cityStateZip= "...";

    @SerializedName("workHistory")
    @Expose
    public List<Map<String,String>> workHistory = new ArrayList<>();

    @SerializedName("education")
    @Expose
    public List<Map<String,String>> education = new ArrayList<>();

    @SerializedName("currentEmployer")
    @Expose
    public String currentEmployer= "";

    @SerializedName("currentPosition")
    @Expose
    public String currentPosition= "";

    @SerializedName("workStreet")
    @Expose
    public String workStreet= "...";

    @SerializedName("workCityStateZip")
    @Expose
    public String workCityStateZip= "...";

    @SerializedName("imageUrl")
    @Expose
    public String imageUrl= "...";


    @SerializedName("connections")
    @Expose
    public Map<String,String> connections = new LinkedHashMap<>();

    @SerializedName("connectionsCount")
    @Expose
    public int connectionsCount;

    @SerializedName("timestamp")
    @Expose
    public double timestamp;

    @SerializedName("dob")
    @Expose
    public double dob;

    @SerializedName("phone")
    @Expose
    public String phone= "...";

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

   @Exclude public String getWorkHistoryString(){

        StringBuilder workHistoryBuilder = new StringBuilder();

        for (Map<String, String> map : workHistory) {

            for (Map.Entry<String,String> entry : map.entrySet()){

                String key = entry.getKey();
                String value = entry.getValue();

                if (!key.toLowerCase().equals("industry")){

                    workHistoryBuilder.append(value + "\n");

                }
            }
            workHistoryBuilder.append("\n");

        }

        return workHistoryBuilder.toString();
    }



    public void setWorkHistory(List<Map<String,String>> workHistory) {
        this.workHistory = workHistory;
    }

    public List<Map<String,String>> getEducation() {

       return education;
    }

  @Exclude  public String getEducationString(){

        StringBuilder educationBuilder = new StringBuilder();

        for (Map<String, String> map : education) {

            for (Map.Entry<String, String> entry : map.entrySet()) {

                String key = entry.getKey();
                String value = entry.getValue();

                if (key.toLowerCase().equals("school")){

                    SpannableString spannableString = new SpannableString(value);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                    spannableString.setSpan(boldSpan, 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    educationBuilder.append(spannableString + "\n");

                } else {

                    educationBuilder.append(value)
                            .append("\n");
                }

            }

            educationBuilder.append("\n");
        }

        return educationBuilder.toString();
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

    public long getTimestamp() {

        String adjustedTime = AppFormatter.timeStampFormatter.format(timestamp);

        return Long.parseLong(adjustedTime);    }

    public long getAdjustedTimeStamp(){

        String adjustedTime = AppFormatter.timeStampFormatter.format(timestamp);

        return Long.parseLong(adjustedTime);
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public long getDob() {

        String adjustedTime = AppFormatter.timeStampFormatter.format(timestamp);

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
