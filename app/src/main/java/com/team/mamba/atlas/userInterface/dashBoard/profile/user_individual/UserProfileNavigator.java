package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual;

public interface UserProfileNavigator {

    void onUserProfileClicked();

    void onSettingsClicked();

    void editPhoneInfo();

    void editEmailInfo();

    void editAddressInfo();

    void editWorkHistoryInfo();

    void editEductionInfo();

    void onProfileUpdated();

    void openCamera();

    void openGallery();

    String getImagePath();

    void onConnectionTypeSaved();
}
