package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;

public interface ContactsNavigator {

    void onAddNewContactClicked();

    void onSyncContactsClicked();

    void onSettingsClicked();

    void onProfileImageClicked();

    void onGroupFilterClicked();

    void onIndividualFilterClicked();

    void onRowClicked(UserConnections userConnections);

    void handleError(String errorMsg);
}
