package com.team.mamba.atlas.data;

import com.team.mamba.atlas.data.appLevel.AppHelper;
import com.team.mamba.atlas.data.appLevel.DataHelper;
import com.team.mamba.atlas.utils.AppSharedPrefs;
import javax.inject.Inject;

public class AppDataManager implements DataHelper{

    private AppHelper appDataHelper;


    @Inject
    public AppDataManager(AppHelper appHelper){

        this.appDataHelper = appDataHelper;
    }

    @Override
    public AppSharedPrefs getSharedPrefs() {
        return appDataHelper.getSharedPrefs();
    }
}
