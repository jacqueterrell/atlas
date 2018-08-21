package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_work_history.edit_work;

import com.team.mamba.atlas.data.model.local.Education;
import com.team.mamba.atlas.data.model.local.WorkHistory;

public interface EditWorkNavigator {


    void onFinishedClicked();
    void onSaveAndCloseClicked();
    void onEditRowSaved(WorkHistory workHistory);
    void onProfileUpdated();
    void onSaveNewWorkHistory(WorkHistory workHistory);
    void onAddNewWork();
    void onRowClicked(WorkHistory workHistory, int pos);
    boolean isEditing();
    WorkHistory getEditingWork();
}
