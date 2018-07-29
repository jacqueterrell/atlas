package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class WelcomeDataModel {

    private AppDataManager dataManager;


    @Inject
    public WelcomeDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }
}
