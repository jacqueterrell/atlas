package com.team.mamba.atlas.userInterface.dashBoard.dashBoardHome;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DashBoardHomeViewModel extends BaseViewModel<DashBoardHomeNavigator> {

    private DashBoardHomeDataModel dataModel;


    public void setDataModel(DashBoardHomeDataModel dashBoardHomeDataModel) {
        this.dataModel = dashBoardHomeDataModel;
    }
}
