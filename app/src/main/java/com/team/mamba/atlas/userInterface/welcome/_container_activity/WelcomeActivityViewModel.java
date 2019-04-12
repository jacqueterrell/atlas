package com.team.mamba.atlas.userInterface.welcome._container_activity;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class WelcomeActivityViewModel extends BaseViewModel<WelcomeActivityNavigator> {


    private boolean businessLogin = false;


    public void setBusinessLogin(boolean businessLogin) {
        this.businessLogin = businessLogin;
    }

    public boolean isBusinessLogin() {
        return businessLogin;
    }
}
