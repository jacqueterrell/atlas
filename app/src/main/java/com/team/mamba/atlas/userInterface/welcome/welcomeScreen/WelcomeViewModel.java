package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {

    private WelcomeDataModel dataModel;

    public void setDataModel(WelcomeDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
