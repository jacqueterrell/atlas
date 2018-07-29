package com.team.mamba.atlas.userInterface.dashBoard.notifications;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class NotificationsDataModel {

    private AppDataManager dataManager;

    @Inject
    public NotificationsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
