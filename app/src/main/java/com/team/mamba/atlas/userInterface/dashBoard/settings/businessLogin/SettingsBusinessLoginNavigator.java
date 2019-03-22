package com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;

public interface SettingsBusinessLoginNavigator {

    void onEmptyBusinessesReturned();

    void onSuccessfulBusinessProfileResponse();

    void onAccountSelected(BusinessProfile profile);

    void handleError(String errorMsg);

}
