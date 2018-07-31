package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {

    private WelcomeDataModel dataModel;

    public void setDataModel(WelcomeDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void onStartButtonClicked(){

        getNavigator().onStartButtonClicked();
    }

    public void onBusinessLoginClicked(){

        getNavigator().onBusinessLoginClicked();
    }

    public void onDateVerifyClicked(){

        getNavigator().onDateVerifyClicked();
    }

    public void onDateCancelClicked(){

        getNavigator().onDateCancelClicked();
    }

    public void onFirstNameNextClicked(){

        getNavigator().onFirstNameNextClicked();
    }

    public void onLastNameNextClicked(){

        getNavigator().onLastNameNextClicked();
    }

    public void onPhoneSubmitClicked(){

        getNavigator().onPhoneSubmitClicked();
    }
}
