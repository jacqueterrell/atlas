package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class CrmDataModel {

    private AppDataManager dataManager;

    @Inject
    public CrmDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void getAllOpportunites(){


    }


}
