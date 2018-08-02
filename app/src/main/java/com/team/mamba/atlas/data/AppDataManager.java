package com.team.mamba.atlas.data;

import com.team.mamba.atlas.data.appLevel.AppHelper;
import com.team.mamba.atlas.data.appLevel.DataHelper;
import com.team.mamba.atlas.utils.AppSharedPrefs;
import javax.inject.Inject;

public class AppDataManager implements DataManager{

    private AppHelper appDataHelper;


    @Inject
    public AppDataManager(AppHelper appHelper){

        this.appDataHelper = appHelper;
    }

    @Override
    public AppSharedPrefs getSharedPrefs() {
        return appDataHelper.getSharedPrefs();
    }
}
