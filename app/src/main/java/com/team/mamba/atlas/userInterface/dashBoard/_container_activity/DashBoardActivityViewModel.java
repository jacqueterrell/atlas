package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DashBoardActivityViewModel extends BaseViewModel<DashBoardActivityNavigator> {



    public void onContactsClicked(){

        getNavigator().onContactsClicked();
    }

    public void onCrmClicked(){

        getNavigator().onCrmClicked();
    }

    public void onNotificationsClicked(){

        getNavigator().onNotificationsClicked();
    }

    public void onInfoClicked(){

        getNavigator().onInfoClicked();
    }
}
