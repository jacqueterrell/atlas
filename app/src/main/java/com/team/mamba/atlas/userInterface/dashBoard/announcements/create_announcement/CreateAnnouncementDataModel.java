package com.team.mamba.atlas.userInterface.dashBoard.announcements.create_announcement;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class CreateAnnouncementDataModel {

    private AppDataManager dataManager;

    @Inject
    public CreateAnnouncementDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void sendAnnouncement(){


    }
}
