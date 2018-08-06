package com.team.mamba.atlas.userInterface.dashBoard._container_activity.describe_connections;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class DescribeConnectionsViewModel extends BaseViewModel<DescribeConnectionsNavigator> {


    private DescribeConnectionsDataModel dataModel;

    private boolean familyMember;
    private boolean personalFriend;
    private boolean newAcquaintance;
    private boolean businessContact;
    private boolean colleague;
    private boolean client;



    /******Getters and Setters******/

    public void setDataModel(DescribeConnectionsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(boolean familyMember) {
        this.familyMember = familyMember;
    }

    public boolean isPersonalFriend() {
        return personalFriend;
    }

    public void setPersonalFriend(boolean personalFriend) {
        this.personalFriend = personalFriend;
    }

    public boolean isNewAcquaintance() {
        return newAcquaintance;
    }

    public void setNewAcquaintance(boolean newAcquaintance) {
        this.newAcquaintance = newAcquaintance;
    }

    public boolean isBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(boolean businessContact) {
        this.businessContact = businessContact;
    }

    public boolean isColleague() {
        return colleague;
    }

    public void setColleague(boolean colleague) {
        this.colleague = colleague;
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
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

    public void addUser(DescribeConnectionsViewModel viewModel,String lastName, String userCode){

        dataModel.addUser(viewModel,lastName,userCode);
    }
}
