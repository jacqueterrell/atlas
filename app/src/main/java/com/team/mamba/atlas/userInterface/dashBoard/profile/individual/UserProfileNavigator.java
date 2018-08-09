package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

public interface UserProfileNavigator {

    void onUserProfileClicked();

    void onSettingsClicked();

    void contactCellPhoneClicked();

    void contactOnOfficePhoneClicked();

    void contactOnHomePhoneClicked();

    void contactOnPersonalPhoneClicked();

    void contactOnFaxClicked();

    void contactOnPersonalEmailClicked();

    void contactOnWorkEmailClicked();

    void contactOnHomeAddressClicked();

    void contactOnWorkAddressClicked();

    void contactOnWorkHistoryClicked();

    void contactOnEducationClicked();

    void editPhoneInfo();

    void editEmailInfo();

    void editAddressInfo();

    void editWorkHistoryInfo();

    void editEductionInfo();

    void onProfileUpdated();
}
