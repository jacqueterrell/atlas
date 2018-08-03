package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DashBoardHomeViewModel extends BaseViewModel<DashBoardHomeNavigator> {

    private InfoDataModel dataModel;


    public void setDataModel(InfoDataModel dashBoardHomeDataModel) {
        this.dataModel = dashBoardHomeDataModel;
    }
}
