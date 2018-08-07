package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.data.model.UserConnections;

import java.util.List;

public interface InfoNavigator {

    void onNetworkButtonClicked();

    void onOpportunitiesButtonClicked();

    void onAddContactClicked();

    void onUserProfileClicked();

    void onSettingsClicked();

    void onAddressBookClicked();

    void onCrmClicked();

    void onNotificationsClicked();

    void onUserStatsInfoClicked();

    void onRecentActivityInfoClicked();

    void hideRecentActivityInfoDialog();

    void hideUserStatusInfoDialog();

    void setNetworkBarChartData();

    void setUserStatsAdapter(List<String> userStats,List<UserConnections> connectionRecords);

    void handlerError(String msg);


}
