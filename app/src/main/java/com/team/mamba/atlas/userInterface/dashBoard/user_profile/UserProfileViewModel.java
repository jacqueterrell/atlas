package com.team.mamba.atlas.userInterface.dashBoard.user_profile;

import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;

public class UserProfileViewModel extends BaseViewModel<UserProfileNavigator> {


    private static UserProfile userProfile;
    private static BusinessProfile businessProfile;
    private UserProfileDataModel dataModel;


    public void setDataModel(UserProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setUserProfile(UserProfile userProfile) {
        UserProfileViewModel.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        UserProfileViewModel.businessProfile = businessProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }


    public void onProfileImageClicked(){

        getNavigator().onUserProfileClicked();
    }

    public void getUserDetails(UserProfileViewModel viewModel){

        dataModel.getUserDetails(viewModel);
    }

    public void getBusinessDetails(UserProfileViewModel viewModel){

        dataModel.getBusinessDetails(viewModel);
    }
}
