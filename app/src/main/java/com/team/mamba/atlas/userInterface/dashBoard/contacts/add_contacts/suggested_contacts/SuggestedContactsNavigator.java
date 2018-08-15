package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts;

import com.team.mamba.atlas.data.model.api.UserProfile;

public interface SuggestedContactsNavigator {

    void onSearchButtonClicked();

    void onSuggestedContactsFound();

    void onUsersRowClicked(UserProfile profile);

    void showInvitationSentAlert(String first,String last);

    void handleError(String msg);


}
