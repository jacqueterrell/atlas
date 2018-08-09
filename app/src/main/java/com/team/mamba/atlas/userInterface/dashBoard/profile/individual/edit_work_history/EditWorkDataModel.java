package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class EditWorkDataModel {


    private AppDataManager dataManager;

    @Inject
    public EditWorkDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
