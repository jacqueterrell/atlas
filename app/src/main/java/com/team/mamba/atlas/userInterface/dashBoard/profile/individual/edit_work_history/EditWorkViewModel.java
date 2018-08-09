package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkDataModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkNavigator;

public class EditWorkViewModel extends BaseViewModel<EditWorkNavigator> {

    private EditWorkDataModel dataModel;


    public void setDataModel(EditWorkDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
