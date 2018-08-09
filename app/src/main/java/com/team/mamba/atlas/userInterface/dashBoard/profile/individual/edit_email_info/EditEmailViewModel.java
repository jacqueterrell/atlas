package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailDataModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailNavigator;

public class EditEmailViewModel extends BaseViewModel<EditEmailNavigator> {

    private EditEmailDataModel dataModel;


    public void setDataModel(EditEmailDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
