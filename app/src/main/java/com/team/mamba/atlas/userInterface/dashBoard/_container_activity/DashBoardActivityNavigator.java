package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;
import com.team.mamba.atlas.userInterface.dashBoard.crm.main.CrmFragment;

public interface DashBoardActivityNavigator {


    void openAddContactDialog();

    void openSettingsScreen();

    void onContactsClicked();

    void openUserProfile(UserProfile userProfile);

    void openBusinessProfile(BusinessProfile businessProfile);

    void onCrmClicked();

    void onNotificationsClicked();

    void onInfoClicked();

    void onCorporateDirectoryClicked();

    void onOrganizationalOutreachClicked();

    void onAlumniNetworkingClicked();

    void onPrivacyPolicyClicked();

    void onTermsOfServiceClicked();

    void onNetworkManagementClicked();

    void onLogOutClicked();

    void onDeleteMyAccountClicked();

    void onSiteLinkClicked();

    void onAddUserClicked();

    void onAddBusinessClicked();

    void onInviteToAtlasClicked();

    void onFindUsersClicked();

    void onAddSuggestedContactsClicked();

    void onCancelAddDialogClicked();

    void showToolBar();

    void hideToolBar();

    void setUserProfile(UserProfile userProfile);

    UserProfile getUserProfile();

    CrmFilter getCrmFilter();

    void setCrmFilter(CrmFilter crmFilter);

}
