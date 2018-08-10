package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DashBoardActivityViewModel extends BaseViewModel<DashBoardActivityNavigator> {


    private UserProfile userProfile;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void onContactsClicked(){

        getNavigator().onContactsClicked();
    }

    public void onCrmClicked(){

        getNavigator().onCrmClicked();
    }

    public void onNotificationsClicked(){

        getNavigator().onNotificationsClicked();
    }

    public void onInfoClicked(){

        getNavigator().onInfoClicked();
    }

    public void onCorporateDirectoryClicked(){

        getNavigator().onCorporateDirectoryClicked();
    }

    public void onOrganizationalOutreachClicked(){

        getNavigator().onOrganizationalOutreachClicked();
    }

    public void onAlumniNetworkingClicked(){

        getNavigator().onAlumniNetworkingClicked();
    }

    public void onPrivacyPolicyClicked(){

        getNavigator().onPrivacyPolicyClicked();
    }

    public void onTermsOfServiceClicked(){

        getNavigator().onTermsOfServiceClicked();
    }

    public void onNetworkManagementClicked(){

        getNavigator().onNetworkManagementClicked();
    }

    public void onLogOutClicked(){

        getNavigator().onLogOutClicked();
    }

    public void onDeleteMyAccountClicked(){

        getNavigator().onDeleteMyAccountClicked();
    }

    public void onSiteLinkClicked(){

        getNavigator().onSiteLinkClicked();;
    }

    public void onAddUserClicked(){

        getNavigator().onAddUserClicked();
    }

    public void onAddBusinessClicked(){

        getNavigator().onAddBusinessClicked();
    }

    public void onInviteToAtlasClicked(){

        getNavigator().onInviteToAtlasClicked();
    }

    public void onFindUsersClicked(){

        getNavigator().onFindUsersClicked();
    }

    public void onAddSuggestedContactsClicked(){

        getNavigator().onAddSuggestedContactsClicked();
    }

    public void onCancelAddDialogClicked(){

        getNavigator().onCancelAddDialogClicked();
    }
}
