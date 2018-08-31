package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DashBoardActivityViewModel extends BaseViewModel<DashBoardActivityNavigator> {


    private UserProfile userProfile;
    private CrmFilter crmFilter;


    public void setCrmFilter(CrmFilter crmFilter) {
        this.crmFilter = crmFilter;
    }

    public CrmFilter getCrmFilter() {
        return crmFilter;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

}
