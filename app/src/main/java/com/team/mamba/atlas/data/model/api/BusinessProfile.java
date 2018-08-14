package com.team.mamba.atlas.data.model.api;

import android.support.annotation.Keep;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Keep
public class BusinessProfile {

    public String id = "...";
    public String code = "...";
    public String name = "...";
    public String contactName = "...";
    public String email = "...";
    public String phone = "...";
    public String fax = "...";
    public String street = "...";
    public String cityStateZip = "...";
    public String imageUrl = "...";
    public Map<String,String> announcements = new LinkedHashMap<>();
    public Map<String,String> children = new LinkedHashMap<>();
    public Map<String,String> contacts = new LinkedHashMap<>();
    public List<Integer> shareNeeds = new LinkedList<>();

    public String businessRepId = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = String.valueOf(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String,String> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Map<String,String> announcements) {
        this.announcements = announcements;
    }

    public Map<String, String> getChildren() {
        return children;
    }

    public void setChildren(Map<String, String> children) {
        this.children = children;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public List<Integer> getShareNeeds() {
        return shareNeeds;
    }

    public void setShareNeeds(List<Integer> shareNeeds) {
        this.shareNeeds = shareNeeds;
    }

    public String getBusinessRepId() {
        return businessRepId;
    }

    public void setBusinessRepId(String businessRepId) {
        this.businessRepId = businessRepId;
    }
}
