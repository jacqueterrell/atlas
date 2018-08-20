package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile;

import android.net.Uri;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class ContactProfileViewModel extends BaseViewModel<ContactProfileNavigator> {


    private ContactProfileDataModel dataModel;
    private UserProfile consentingProfile;
    private String selectedPhone;


    /*************Getters and Setters*****************/

    public void setDataModel(ContactProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setConsentingProfile(UserProfile consentingProfile) {
        this.consentingProfile = consentingProfile;
    }

    public UserProfile getConsentingProfile() {
        return consentingProfile;
    }

    public String getSelectedPhone() {
        return selectedPhone;
    }

    public void setSelectedPhone(String selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    /**************OnClick Listeners**********************/

    public void contactCellPhoneClicked() {

        getNavigator().contactCellPhoneClicked();
    }


    public void contactOnOfficePhoneClicked() {

        getNavigator().contactOnOfficePhoneClicked();
    }


    public void contactOnHomePhoneClicked() {

        getNavigator().contactOnHomePhoneClicked();
    }


    public void contactOnPersonalPhoneClicked() {

        getNavigator().contactOnPersonalPhoneClicked();
    }


    public void contactOnFaxClicked() {

        getNavigator().contactOnFaxClicked();
    }


    public void contactOnPersonalEmailClicked() {

        getNavigator().contactOnPersonalEmailClicked();
    }


    public void contactOnWorkEmailClicked() {

        getNavigator().contactOnWorkEmailClicked();
    }


    public void contactOnHomeAddressClicked() {

        getNavigator().contactOnHomeAddressClicked();
    }


    public void contactOnWorkAddressClicked() {

        getNavigator().contactOnWorkAddressClicked();
    }


    public void contactOnWorkHistoryClicked() {

        getNavigator().contactOnWorkHistoryClicked();
    }


    public void contactOnEducationClicked() {

        getNavigator().contactOnEducationClicked();
    }


    /****************DataModel Requests***************/

    public void getConnectionType(ContactProfileViewModel viewModel,UserProfile userProfile){

        dataModel.getConnectionType(viewModel,userProfile);
    }

}