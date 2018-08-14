package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DescribeConnectionsViewModel extends BaseViewModel<DescribeConnectionsNavigator> {


    private DescribeConnectionsDataModel dataModel;

    private UserProfile loggedInProfile;



    /******Getters and Setters******/

    public void setDataModel(DescribeConnectionsDataModel dataModel) {
        this.dataModel = dataModel;
    }


    public void setLoggedInProfile(UserProfile loggedInProfile) {
        this.loggedInProfile = loggedInProfile;
    }

    public UserProfile getLoggedInProfile() {
        return loggedInProfile;
    }

    /*****Onclick Listeners*********/

    public void onFinishButtonClicked(){

        getNavigator().onFinishButtonClicked();
    }

    public void onInfoClicked(){

        getNavigator().onInfoClicked();
    }

    public void hideConnectionsInfo(){

        getNavigator().hideConnectionsInfo();
    }

    /*****DataModel calls******/

    public void addUserRequest(DescribeConnectionsViewModel viewModel,String lastName, String userCode,int connectionType){

        dataModel.addNewRequest(viewModel,lastName,userCode,connectionType);
    }
}
