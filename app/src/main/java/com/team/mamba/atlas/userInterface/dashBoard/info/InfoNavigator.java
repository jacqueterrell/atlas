package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.data.model.ConnectionRecord;
import java.util.List;

public interface InfoNavigator {

    void onNetworkButtonClicked();

    void onOpportunitiesButtonClicked();

    void onAddButtonClicked();

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

    void setUserStatsAdapter(List<String> userStats,List<ConnectionRecord> connectionRecords);

    void handlerError(String msg);


}
