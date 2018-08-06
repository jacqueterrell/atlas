package com.team.mamba.atlas.utils;

import com.team.mamba.atlas.BuildConfig;

public class AppConstants {

    private AppConstants() {
        //disable new object creation
    }

    public static boolean APP_DEBUG = BuildConfig.DEBUG;
    public static final String USERS_COLLECTION = "users";
    public static final String BUSINESSES_COLLECTION = "businesses";
    public static final String CONNECTIONS_COLLECTION = "connections";
    public static final String BUS_NOTES_COLLECTION = "busNotes";
    public static String TEST_USER_ID = "S0URPfcKiVanC5NhHd4n9ejcEWZ2";
    public static final String BASE_PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";

}
