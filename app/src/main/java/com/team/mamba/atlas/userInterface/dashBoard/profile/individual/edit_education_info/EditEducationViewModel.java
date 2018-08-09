package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationDataModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationNavigator;

public class EditEducationViewModel extends BaseViewModel<EditEducationNavigator> {

    private EditEducationDataModel dataModel;

    public void setDataModel(EditEducationDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
