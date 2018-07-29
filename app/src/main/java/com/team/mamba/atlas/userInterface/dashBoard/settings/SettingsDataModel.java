package com.team.mamba.atlas.userInterface.dashBoard.settings;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class SettingsDataModel {

    private AppDataManager dataManager;


    @Inject
    public  SettingsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
