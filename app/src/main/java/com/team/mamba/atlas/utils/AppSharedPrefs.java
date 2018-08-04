package com.team.mamba.atlas.utils;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class AppSharedPrefs {


    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String USER_LOGGED_IN = "userLoggedIn";


    private SharedPreferences sharedPreferences;

    @Inject
    public AppSharedPrefs(SharedPreferences preferences) {
        sharedPreferences = preferences;
    }

    public AppSharedPrefs() {

    }

    public void setUserId(String id){

        sharedPreferences
                .edit()
                .putString(USER_ID,id)
                .apply();
    }

    public String getUserId(){

        return sharedPreferences
                .getString(USER_ID,"");
    }

    public void setFirstName(String firstName){

        sharedPreferences.edit()
                .putString(FIRST_NAME,firstName)
                .apply();
    }

    public String getFirstName(){

        return sharedPreferences
                .getString(FIRST_NAME,"");
    }

    public void setLastName(String lastName){

        sharedPreferences
                .edit()
                .putString(LAST_NAME,lastName)
                .apply();
    }

    public String getLastName(){

        return sharedPreferences
                .getString(LAST_NAME,"");
    }

    public void setPhoneNumber(String phoneNumber){

        sharedPreferences.edit()
                .putString(PHONE_NUMBER,phoneNumber)
                .apply();
    }

    public String getPhoneNumber(){

        return sharedPreferences
                .getString(PHONE_NUMBER,"");
    }

    public boolean isUserLoggedIn(){

        return sharedPreferences
                .getBoolean(USER_LOGGED_IN,false);
    }

    public void setUserLoggedIn(boolean userLoggedIn){

        sharedPreferences
                .edit()
                .putBoolean(USER_LOGGED_IN,userLoggedIn)
                .apply();
    }
}
