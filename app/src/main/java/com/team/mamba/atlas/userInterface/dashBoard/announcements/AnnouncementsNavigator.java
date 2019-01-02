package com.team.mamba.atlas.userInterface.dashBoard.announcements;

public interface AnnouncementsNavigator {

    void onAnnouncementsReturned();

    void onAddAnnouncementClicked();

    void handleError(String errorMsg);

    void onCrmClicked();

    void onInfoClicked();

    void onContactsClicked();
}
