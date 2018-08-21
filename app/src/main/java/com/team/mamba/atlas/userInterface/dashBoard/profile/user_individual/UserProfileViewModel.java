package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual;

import android.net.Uri;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.utils.formatData.RegEx;

public class UserProfileViewModel extends BaseViewModel<UserProfileNavigator> {


    private static BusinessProfile businessProfile;
    private UserProfileDataModel dataModel;
    private Uri imageUri = null;
    private String selfiePath = null;


    /*******Getters and Setters********/
    public void setDataModel(UserProfileDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        UserProfileViewModel.businessProfile = businessProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }


    public void onProfileImageClicked() {

        getNavigator().onUserProfileClicked();
    }

    public void getUserDetails(UserProfileViewModel viewModel, String userId) {

        dataModel.getUserDetails(viewModel, userId);
    }


    public String getSelfiePath() {
        return selfiePath;
    }

    public void setSelfiePath(String s) {
        this.selfiePath = s;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri galleryImageUri) {
        imageUri = galleryImageUri;
    }

    /*******Onclick Listeners********/
    public void onSettingsClicked() {

        getNavigator().onSettingsClicked();
    }

    public void editPhoneInfo() {

        getNavigator().editPhoneInfo();
    }


    public void editEmailInfo() {

        getNavigator().editEmailInfo();
    }


    public void editAddressInfo() {

        getNavigator().editAddressInfo();
    }


    public void editWorkHistoryInfo() {

        getNavigator().editWorkHistoryInfo();
    }


    public void editEductionInfo() {

        getNavigator().editEductionInfo();
    }

    public boolean isStringValid(String phone) {

        try {
            String adjustedPhone = phone.replaceAll(RegEx.REMOVE_NON_DIGITS, "");
        } catch (Exception e) {

            return false;
        }

        return true;
    }


    public void uploadImage(UserProfileViewModel viewModel, UserProfile profile) {

        dataModel.uploadImage(viewModel, profile);
    }

}
