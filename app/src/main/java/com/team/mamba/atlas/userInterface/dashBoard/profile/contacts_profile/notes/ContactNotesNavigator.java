package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import com.team.mamba.atlas.data.model.api.PersonalNotes;

public interface ContactNotesNavigator {

    void onUserNotesReturned();

    void onEditDetailsClicked();

    void onConnectionInfoClicked();

    void onNotesInfoClicked();

    void hideConnectionInfoDialog();

    void hidePersonalNotesInfoDialog();
}
