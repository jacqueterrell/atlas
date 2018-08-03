package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class InfoDataModel {

    private AppDataManager dataManager;


    @Inject
    public InfoDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
