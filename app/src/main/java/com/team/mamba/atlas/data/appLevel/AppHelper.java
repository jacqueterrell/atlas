package com.team.mamba.atlas.data.appLevel;

import android.content.Context;
import com.team.mamba.atlas.utils.AppSharedPrefs;
import javax.inject.Inject;

public class AppHelper implements DataHelper {

    private final AppSharedPrefs sharedPrefs;
    private final Context appContext;

    @Inject
    public AppHelper(Context appContext,AppSharedPrefs sharedPrefs){

        this.appContext = appContext;
        this.sharedPrefs = sharedPrefs;
    }
}
