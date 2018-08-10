package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.team.mamba.atlas.data.model.api.CrmNotes;

public interface CrmNavigator {

    void onCrmDataReturned();

    void onRowClicked(CrmNotes notes);

    void onInfoClicked();
}
