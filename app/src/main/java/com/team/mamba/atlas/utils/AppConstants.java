package com.team.mamba.atlas.utils;

import com.team.mamba.atlas.BuildConfig;

public class AppConstants {

    private AppConstants() {
        //disable new object creation
    }

    public static boolean APP_DEBUG = BuildConfig.DEBUG;
    public static final String USERS_COLLECTION = "users";
    public static final String BUSINESSES_COLLECTION = "businesses";

}
