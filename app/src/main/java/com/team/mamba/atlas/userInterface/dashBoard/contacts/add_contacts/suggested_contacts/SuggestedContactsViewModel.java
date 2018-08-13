package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts.SuggestedContactsDataModel;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts.SuggestedContactsNavigator;

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
