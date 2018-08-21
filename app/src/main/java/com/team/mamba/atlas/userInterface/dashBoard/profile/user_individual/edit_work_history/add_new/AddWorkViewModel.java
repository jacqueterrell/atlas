package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_work_history.add_new;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class AddWorkViewModel extends BaseViewModel<AddWorkNavigator> {

    private AddWorkDataModel dataModel;


    public void setDataModel(AddWorkDataModel dataModel) {
        this.dataModel = dataModel;
    }




    /**************DataModel Requests*******************/

    public void getGooglePlaceHints(AddWorkViewModel viewModel,String text){

        dataModel.getGooglePlaceHints(viewModel,text);
    }
}
