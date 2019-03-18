package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users.recycler_view;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;

public interface FindUsersRecyclerNavigator {

    void onUsersRowClicked(UserProfile profile);

    void showAlreadyAContactAlert();

    void showAlreadySentRequestAlert();

    void showInvitationSentAlert(String first,String last);

    void handleError(String msg);

}
