package com.team.mamba.atlas.userInterface.dashBoard.announcements.create_announcement;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class CreateAnnouncementViewModel extends BaseViewModel<CreateAnnouncementNavigator> {

    private CreateAnnouncementDataModel dataModel;
    private boolean announcment = true;

    /*************Getters and Setters***************/

    public void setDataModel(
            CreateAnnouncementDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setAnnouncment(boolean announcment) {
        this.announcment = announcment;
    }

    public boolean isAnnouncment() {
        return announcment;
    }

    /***********Onclick Listeners**************/

    public void onSendButtonClicked(){

        getNavigator().onSendButtonClicked();
    }
}
