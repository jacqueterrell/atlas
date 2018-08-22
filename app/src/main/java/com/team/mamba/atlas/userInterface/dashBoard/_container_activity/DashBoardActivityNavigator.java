package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;

public interface DashBoardActivityNavigator {


    void openAddContactDialog();

    void openSettingsScreen();

    void onContactsClicked();

    void openUserProfile(UserProfile userProfile);

    void openBusinessProfile(BusinessProfile businessProfile);

    void onCrmClicked();

    void onNotificationsClicked();

    void onInfoClicked();

    void showToolBar();

    void hideToolBar();

    void setUserProfile(UserProfile userProfile);

    UserProfile getUserProfile();

    CrmFilter getCrmFilter();

    void setCrmFilter(CrmFilter crmFilter);

    void resetToFirstFragment();

    void refreshInfoFragment();

    boolean wasContactRecentlyDeleted();

    void setContactRecentlyDeleted(boolean wasDeleted);
}
