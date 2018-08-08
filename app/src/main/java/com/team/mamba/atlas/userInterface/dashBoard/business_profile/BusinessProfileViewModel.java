package com.team.mamba.atlas.userInterface.dashBoard.business_profile;

import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class BusinessProfileViewModel extends BaseViewModel<BusinessProfileNavigator> {

    private static BusinessProfile businessProfile;
    private BusinessProfileDataModel dataModel;



    /*******Getters and Setters************/
    public void setDataModel(BusinessProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        BusinessProfileViewModel.businessProfile = businessProfile;
    }


    /*******Onclick Listeners*******/
    public void onProfileImageClicked(){

        getNavigator().onProfileImageClicked();
    }


}
