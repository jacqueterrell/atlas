package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class ContactsViewModel extends BaseViewModel<ContactsNavigator> {

    ContactsDataModel dataModel;


    public void setDataModel(ContactsDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
