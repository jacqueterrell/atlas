package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DescribeConnectionsViewModel extends BaseViewModel<DescribeConnectionsNavigator> {


    private DescribeConnectionsDataModel dataModel;

    private UserProfile loggedInProfileIndividual;
    private BusinessProfile loggedInProfileBusiness;



    /******Getters and Setters******/

    public void setDataModel(DescribeConnectionsDataModel dataModel) {
        this.dataModel = dataModel;
    }


    public void setLoggedInProfileIndividual(UserProfile loggedInProfileIndividual) {
        this.loggedInProfileIndividual = loggedInProfileIndividual;
    }

    public UserProfile getLoggedInProfileIndividual() {
        return loggedInProfileIndividual;
    }

    public void setLoggedInProfileBusiness(BusinessProfile loggedInProfileBusiness) {
        this.loggedInProfileBusiness = loggedInProfileBusiness;
    }

    public BusinessProfile getLoggedInProfileBusiness() {
        return loggedInProfileBusiness;
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

        dataModel.initiateNewUserRequest(viewModel,lastName,userCode,connectionType);
    }
}
