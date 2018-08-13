package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class FindUsersViewModel extends BaseViewModel<FindUsersNavigator> {

    private FindUsersDataModel dataModel;


    /*****Getters and Setters*****/

    public void setDataModel(FindUsersDataModel dataModel) {
        this.dataModel = dataModel;
    }


    /*****Onclick Listeners********/

    public void onSearchButtonClicked(){

        getNavigator().onSearchButtonClicked();
    }

    /*****Datamodel calls*****/
    public void queryUsers(FindUsersViewModel viewModel){

        dataModel.queryUsers(viewModel);
    }
}
