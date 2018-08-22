package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_email_info;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class EditEmailViewModel extends BaseViewModel<EditEmailNavigator> {

    private EditEmailDataModel dataModel;



    public void setDataModel(EditEmailDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void onContinueClicked(){

        getNavigator().onContinueClicked();
    }

    public void onSaveAndCloseClicked(){
        getNavigator().onSaveAndCloseClicked();
    }

    public void updateUser(EditEmailViewModel viewModel, UserProfile profile){

        dataModel.updateUser(viewModel,profile);
    }
}
