package com.team.mamba.atlas.userInterface.dashBoard.settings;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.List;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {


    private SettingsDataModel dataModel;


    private List<BusinessProfile> businessProfileList;


    /**************Getters and Setters******************/

    public void setDataModel(SettingsDataModel dataModel) {
        this.dataModel = dataModel;
    }


    public void setBusinessProfileList(List<BusinessProfile> businessProfileList) {
        this.businessProfileList = businessProfileList;
    }

    public List<BusinessProfile> getBusinessProfileList() {
        return businessProfileList;
    }

    /************* Click Listeners *************/

    public void onCorporateDirectoryClicked(){
        getNavigator().onCorporateDirectoryClicked();
    }

    public void onOrganizationalOutreachClicked(){
        getNavigator().onOrganizationalOutreachClicked();
    }

    public void onUserLoginClicked(){
        getNavigator().onUserLoginClicked();
    }

    public void onBusinessLoginClick(){
        getNavigator().onBusinessLoginClick();
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

    /**************DataModel Requests**********************/

    public void deleteUser(){

        dataModel.deleteUser();
    }
}
