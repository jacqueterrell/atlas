package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_business;

public interface AddBusinessNavigator {

    void onFinishButtonClicked();

    void showUserNotFoundAlert();

    void showAlreadyAContactAlert();

    void onRequestSent();
}
