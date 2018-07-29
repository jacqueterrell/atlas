package com.team.mamba.atlas.userInterface.dashBoard.settings;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {

    private SettingsDataModel dataModel;


    public void setDataModel(SettingsDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
