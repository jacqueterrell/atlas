package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;

import net.hockeyapp.android.metrics.model.User;

import java.util.List;

public interface ContactsNavigator {

    void onAddNewContactClicked();

    void onSyncContactsClicked();

    void onSettingsClicked();

    void onProfileImageClicked();

    void onBusinessFilterClicked();

    void onIndividualFilterClicked();

    void onRowClicked(UserConnections userConnections);

    void handleError(String errorMsg);

    void onDataValuesReturned(List<UserConnections> connectionsList);

    void onBusinessContactsSet(List<UserConnections> businessAssociatesList);

    boolean isActivityVisible();

    List<UserConnections> getPermConnectionList();

    void onInfoClicked();

    void onNotificationsClicked();

    void onCrmClicked();
}
