package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;

public interface DashBoardActivityNavigator {


    void setUpLocalNotifications(UserProfile profile);
    void openAddContactDialog();

    void openSettingsScreen();

    void openUserProfile(UserProfile userProfile);

    void openBusinessProfile(BusinessProfile businessProfile);

    void setUserProfile(UserProfile userProfile);

    UserProfile getUserProfile();

    CrmFilter getCrmFilter();

    void setCrmFilter(CrmFilter crmFilter);

    void resetToFirstFragment();

    void resetEntireApp();

    boolean wasContactRecentlyDeleted();

    boolean wasContactRecentlyAdded();

    void setContactRecentlyAdded(boolean wasAdded);

    void setContactRecentlyDeleted(boolean wasDeleted);

}
