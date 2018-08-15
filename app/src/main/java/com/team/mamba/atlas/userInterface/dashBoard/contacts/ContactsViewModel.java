package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsViewModel extends BaseViewModel<ContactsNavigator> {

    ContactsDataModel dataModel;


    private List<UserProfile> userProfileList = new ArrayList<>();
    private static List<BusinessProfile>businessProfileList = new ArrayList<>();
    private static UserProfile userProfile;
    private static BusinessProfile businessProfile;
    private static List<UserConnections> userConnectionsList = new ArrayList<>();
    private static String savedUserId = "";

    private static BusinessProfile selectedBusinessProfile;


    public void setDataModel(ContactsDataModel dataModel) {
        this.dataModel = dataModel;
    }

    /******Getters and Setters********/

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }

    public void setUserProfileList(List<UserProfile> userProfileList) {
        this.userProfileList = userProfileList;
    }

    public List<BusinessProfile> getBusinessProfileList() {
        return businessProfileList;
    }

    public void setBusinessProfileList(List<BusinessProfile> businessProfileList) {
        ContactsViewModel.businessProfileList = businessProfileList;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        ContactsViewModel.userProfile = userProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        ContactsViewModel.businessProfile = businessProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setUserConnectionsList(List<UserConnections> userConnectionsList) {
        ContactsViewModel.userConnectionsList = userConnectionsList;
    }

    public List<UserConnections> getUserConnectionsList() {
        return userConnectionsList;
    }

    public void setSavedUserId(String savedUserId) {
        ContactsViewModel.savedUserId = savedUserId;
    }

    public String getSavedUserId() {
        return savedUserId;
    }

    public void setSelectedBusinessProfile(BusinessProfile selectedBusinessProfile) {
        ContactsViewModel.selectedBusinessProfile = selectedBusinessProfile;
    }

    public BusinessProfile getSelectedBusinessProfile() {
        return selectedBusinessProfile;
    }

    /********Onclick Listeners********/

    public void onAddNewContactClicked(){

        getNavigator().onAddNewContactClicked();
    }

    public void onSycnContactsClicked(){

        getNavigator().onSyncContactsClicked();
    }

    public void onSettingsClicked(){

        getNavigator().onSettingsClicked();
    }

    public void onProfileImageClicked(){

        getNavigator().onProfileImageClicked();
    }

    public void onGroupFilterClicked(){

        getNavigator().onBusinessFilterClicked();
    }

    public void onIndividualFilterClicked(){

        getNavigator().onIndividualFilterClicked();
    }

    /*********DataModel Requests*******/

    public void requestContactsInfo(ContactsViewModel viewModel){

        dataModel.requestContactsInfo(viewModel);
    }
}
