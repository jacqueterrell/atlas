package com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler.BusinessAccountsNavigator;

public class BusinessAccountsViewModel extends BaseViewModel<BusinessAccountsNavigator> {

    private BusinessAccountsDataModel dataModel;

    public void setDataModel(BusinessAccountsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void updateBusinessProfile(BusinessProfile profile) {
        dataModel.updateBusinessProfile(this,profile);
    }
}
