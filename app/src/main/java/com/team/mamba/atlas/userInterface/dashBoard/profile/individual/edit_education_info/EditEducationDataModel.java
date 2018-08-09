package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class EditEducationDataModel {

    private AppDataManager dataManager;


    @Inject
    public EditEducationDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
