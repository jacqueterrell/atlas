package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.data.AppDataManager;
import javax.inject.Inject;

public class ContactsDataModel {

    private AppDataManager dataManager;


    @Inject
    public ContactsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }
}
