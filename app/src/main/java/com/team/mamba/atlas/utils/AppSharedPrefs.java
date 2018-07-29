package com.team.mamba.atlas.utils;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class AppSharedPrefs {

    private SharedPreferences sharedPreferences;

    @Inject
    public AppSharedPrefs(SharedPreferences preferences) {
        sharedPreferences = preferences;
    }

    public AppSharedPrefs() {

    }
}
