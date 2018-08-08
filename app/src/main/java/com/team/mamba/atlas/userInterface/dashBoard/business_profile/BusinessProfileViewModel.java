package com.team.mamba.atlas.userInterface.dashBoard.business_profile;

import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class BusinessProfileViewModel extends BaseViewModel<BusinessProfileNavigator> {

    BusinessProfile businessProfile;
    BusinessProfileDataModel dataModel;



    /*******Getters and Setters************/
    public void setDataModel(BusinessProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    /*******Onclick Listeners*******/
    public void onProfileImageClicked(){

        getNavigator().onProfileImageClicked();
    }


    /*******DataModel calls*******/
    public void getBusinessDetails(BusinessProfileViewModel viewModel){

        dataModel.getBusinessDetails(viewModel);
    }
}
