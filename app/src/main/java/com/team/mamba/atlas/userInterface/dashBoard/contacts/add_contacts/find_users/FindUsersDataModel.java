package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users;

import com.team.mamba.atlas.data.AppDataManager;

import javax.inject.Inject;

public class FindUsersDataModel {

    private AppDataManager dataManager;

    @Inject
    public FindUsersDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void queryUsers(FindUsersViewModel viewModel){


    }
}
