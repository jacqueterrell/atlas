package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_address_info;

import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class EditAddressViewModel extends BaseViewModel<EditAddressNavigator> {

    private EditAddressDataModel dataModel;


    public void setDataModel(EditAddressDataModel dataModel) {
        this.dataModel = dataModel;
    }


    public void updateUser(EditAddressViewModel viewModel,UserProfile profile){

        dataModel.updateUser(viewModel,profile);
    }

    public void onSaveAndCloseClicked(){

        getNavigator().onSaveAndCloseClicked();
    }

    public void onEducationClicked(){

        getNavigator().onEducationClicked();
    }
}
