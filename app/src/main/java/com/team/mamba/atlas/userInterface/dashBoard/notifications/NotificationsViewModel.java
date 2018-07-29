package com.team.mamba.atlas.userInterface.dashBoard.notifications;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class NotificationsViewModel extends BaseViewModel<NotificationsNavigator> {

    private NotificationsDataModel dataModel;


    public void setDataModel(NotificationsDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
