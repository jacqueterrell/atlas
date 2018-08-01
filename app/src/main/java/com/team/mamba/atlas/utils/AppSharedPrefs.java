package com.team.mamba.atlas.utils;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class AppSharedPrefs {


    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

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
}
