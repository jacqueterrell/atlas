package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.data.model.api.UserConnections;

import java.util.List;

public interface InfoNavigator {

    void onNetworkButtonClicked();

    void onOpportunitiesButtonClicked();

    void onAddContactClicked();

    void onUserProfileClicked();

    void onSettingsClicked();

    void onUserStatsInfoClicked();

    void onRecentActivityInfoClicked();

    void hideRecentActivityInfoDialog();

    void hideUserStatusInfoDialog();

    void setBarChartData();

    void setUserStatsAdapter(List<String> userStats,List<UserConnections> connectionRecords);

    void handleError(String msg);

    void onRecentActivitiesRowClicked(UserConnections userConnections);

    void restartApplication();

}
