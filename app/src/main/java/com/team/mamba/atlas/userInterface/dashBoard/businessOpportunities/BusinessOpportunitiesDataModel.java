package com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class BusinessOpportunitiesDataModel {

    private AppDataManager dataManager;

    @Inject
    public BusinessOpportunitiesDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
