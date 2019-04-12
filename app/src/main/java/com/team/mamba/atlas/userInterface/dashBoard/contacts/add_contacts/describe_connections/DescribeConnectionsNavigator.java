package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

public interface DescribeConnectionsNavigator {

    void onFinishButtonClicked();

    void onInfoClicked();

    void hideConnectionsInfo();

    void showUserNotFoundAlert();

    void showAlreadyAContactAlert();

    void showAlreadySentRequestAlert();

    void onRequestSent();

    void onConnectionRequestApproved();

    void onConnectionUpdated();

    void handleError(String errorMsg);
}
