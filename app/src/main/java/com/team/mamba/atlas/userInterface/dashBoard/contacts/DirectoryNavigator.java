package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;

public interface DirectoryNavigator {

    void onRowClicked(UserConnections connections);
}
