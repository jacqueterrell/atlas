package com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminAccountsViewModel extends BaseViewModel<AdminAccountsNavigator> {


    private AdminAccountsDataModel dataModel;
    private List<UserProfile> adminProfileList = new ArrayList<>();

    /************Getters and Setters**************/

    public AdminAccountsDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(AdminAccountsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<UserProfile> getAdminProfileList() {
        return adminProfileList;
    }

    public void setAdminProfileList(List<UserProfile> adminProfileList) {
        this.adminProfileList = adminProfileList;
    }


    /***************DataModel Requests*****************/

    public void getAllAdminProfiles(AdminAccountsViewModel viewModel) {

        dataModel.getAllAdminProfiles(viewModel);
    }
}
