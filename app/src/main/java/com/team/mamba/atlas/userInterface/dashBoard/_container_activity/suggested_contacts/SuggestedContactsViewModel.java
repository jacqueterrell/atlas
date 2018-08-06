package com.team.mamba.atlas.userInterface.dashBoard._container_activity.suggested_contacts;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class SuggestedContactsViewModel extends BaseViewModel<SuggestedContactsNavigator> {

    private SuggestedContactsDataModel dataModel;


    /****Getters and Setters******/

    public void setDataModel(SuggestedContactsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    /*****Onclick Listeners*******/

    public void onSearchButtonClicked(){

        getNavigator().onSearchButtonClicked();
    }
}
