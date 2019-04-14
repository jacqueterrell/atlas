package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes.edit_note;

public interface PersonalNoteNavigator {

    void onAddButtonClicked();

    void onNoteSentSuccessfully();

    void onFinishButtonClicked();

    void handleError(String errorMsg);
}
