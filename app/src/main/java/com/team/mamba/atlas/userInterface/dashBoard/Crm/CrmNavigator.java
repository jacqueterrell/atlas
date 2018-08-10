package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.team.mamba.atlas.data.model.api.CrmNotes;

public interface CrmNavigator {

    void onCrmDataReturned();

    void onRowClicked(CrmNotes notes);

    void onInfoClicked();

    void onSettingsClicked();

    void onShareClicked();

    void onCreateNewNoteClicked();

    void onFilterClicked();

    void hideCrmInfoDialog();

    boolean isInfoDialogShown();

    void onExportButtonClicked();

    boolean isExportScreenShown();

    void hideExportScreen();

}
