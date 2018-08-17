package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users;

import com.team.mamba.atlas.data.model.api.UserProfile;

public interface FindUsersNavigator {

    void onSearchButtonClicked();

    void showUsersNotFoundAlert();

    void onUsersFound();


}
