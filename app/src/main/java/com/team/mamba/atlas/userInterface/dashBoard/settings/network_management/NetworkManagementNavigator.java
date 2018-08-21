package com.team.mamba.atlas.userInterface.dashBoard.settings.network_management;

import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;

public interface NetworkManagementNavigator {

    void onContactListReceived();

    void handleError(String msg);

    void onRowClicked(UserConnections connection);
}
