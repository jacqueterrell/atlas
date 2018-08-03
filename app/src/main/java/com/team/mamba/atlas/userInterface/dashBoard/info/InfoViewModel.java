package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class InfoViewModel extends BaseViewModel<InfoNavigator> {

    private InfoDataModel dataModel;


    public void setDataModel(InfoDataModel dashBoardHomeDataModel) {
        this.dataModel = dashBoardHomeDataModel;
    }

    public void onNetworkButtonClicked(){

        getNavigator().onNetworkButtonClicked();
    }

    public void onOpportunitiesButtonClicked(){

        getNavigator().onOpportunitiesButtonClicked();
    }

    public void onAddButtonClicked(){

        getNavigator().onAddButtonClicked();
    }

    public void onUserProfileClicked(){

        getNavigator().onUserProfileClicked();
    }

    public void onSettingsClicked(){

        getNavigator().onSettingsClicked();
    }

    public void onAddressBookClicked(){

        getNavigator().onAddressBookClicked();
    }

    public void onCrmClicked(){

        getNavigator().onCrmClicked();
    }

    public void onNotificationsClicked(){

        getNavigator().onNotificationsClicked();
    }
}
