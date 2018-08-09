package com.team.mamba.atlas.userInterface.dashBoard.user_profile.edit_address_info;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class EditAddressDataModel {

    private AppDataManager dataManager;


    @Inject
    public EditAddressDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
