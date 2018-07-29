package com.team.mamba.atlas.userInterface.dashBoard.dashBoardHome;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class DashBoardHomeDataModel {

    private AppDataManager dataManager;


    @Inject
    public DashBoardHomeDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
