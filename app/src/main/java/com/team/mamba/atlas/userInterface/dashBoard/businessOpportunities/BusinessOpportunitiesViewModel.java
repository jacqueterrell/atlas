package com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class BusinessOpportunitiesViewModel extends BaseViewModel<BusinessOpportunitiesNavigator> {

    private BusinessOpportunitiesDataModel dataModel;


    public void setDataModel(BusinessOpportunitiesDataModel dataModel) {
        this.dataModel = dataModel;
    }


}
