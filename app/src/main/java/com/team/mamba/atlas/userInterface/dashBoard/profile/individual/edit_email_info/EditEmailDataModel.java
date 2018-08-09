package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class EditEmailDataModel {

    private AppDataManager dataManager;


    @Inject
    public EditEmailDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
