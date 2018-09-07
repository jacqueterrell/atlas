package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_employer;

import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class EditEmployerViewModel extends BaseViewModel<EditEmployerNavigator> {

    private EditEmployerDataModel dataModel;


    /***********Getters and Setters***********/
    public void setDataModel(EditEmployerDataModel dataModel) {
        this.dataModel = dataModel;
    }

    /*********OnClick Listeners****************/
    public void onContinueClicked(){

        getNavigator().onContinueClicked();
    }

    public void onSaveAndCloseClicked(){
        getNavigator().onSaveAndCloseClicked();
    }

    /***********DataModel Requests***************/
    public void updateUser(EditEmployerViewModel viewModel, UserProfile profile){

        dataModel.updateUser(viewModel,profile);
    }
}
