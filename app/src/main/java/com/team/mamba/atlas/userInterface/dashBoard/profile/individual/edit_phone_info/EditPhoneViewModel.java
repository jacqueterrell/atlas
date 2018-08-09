package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_phone_info;

import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class EditPhoneViewModel extends BaseViewModel<EditPhoneNavigator> {

    private EditPhoneDataModel dataModel;


    public void setDataModel(EditPhoneDataModel dataModel) {
        this.dataModel = dataModel;
    }



    public void onSaveAndCloseClicked(){

        getNavigator().onSaveAndCloseClicked();
    }

    public void onContinueClicked(){

        getNavigator().onContinueClicked();
    }

    public void updateUser(EditPhoneViewModel viewModel, UserProfile profile){

        dataModel.updateUser(viewModel,profile);
    }
}
