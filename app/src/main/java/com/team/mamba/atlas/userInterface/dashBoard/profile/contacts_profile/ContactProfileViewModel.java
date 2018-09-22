package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class ContactProfileViewModel extends BaseViewModel<ContactProfileNavigator> {


    private ContactProfileDataModel dataModel;
    private String profilePhone;

    /*************Getters and Setters*****************/

    public void setDataModel(ContactProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setProfilePhone(String profilePhone) {
        this.profilePhone = profilePhone;
    }

    public String getProfilePhone() {
        return profilePhone;
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

}
