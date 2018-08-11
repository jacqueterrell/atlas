package com.team.mamba.atlas.userInterface.dashBoard.Crm.main;

import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

public class CrmViewModel extends BaseViewModel<CrmNavigator> {

    private CrmDataModel dataModel;
    public static List<CrmNotes> crmNotesList = new ArrayList<>();
    private static List<UserProfile> usersContactProfiles = new ArrayList<>();
    private static UserProfile userProfile;
    private static CrmNotes selectedOpportunity;
    private int selectedRowPosition;


    public void setDataModel(CrmDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setCrmNotesList(List<CrmNotes> crmNotesList) {
        CrmViewModel.crmNotesList = crmNotesList;
    }

    public List<CrmNotes> getCrmNotesList() {
        return crmNotesList;
    }

    public void setUsersContactProfiles(List<UserProfile> usersContactProfiles) {
        CrmViewModel.usersContactProfiles = usersContactProfiles;
    }

    public List<UserProfile> getUsersContactProfiles() {
        return usersContactProfiles;
    }

    public void setUserProfile(UserProfile userProfile) {
        CrmViewModel.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setSelectedOpportunity(CrmNotes selectedOpportunity) {
        CrmViewModel.selectedOpportunity = selectedOpportunity;
    }

    public CrmNotes getSelectedOpportunity() {
        return selectedOpportunity;
    }

    public void setSelectedRowPosition(int selectedRowPosition) {
        this.selectedRowPosition = selectedRowPosition;
    }

    public int getSelectedRowPosition() {
        return selectedRowPosition;
    }

    /******OnClick Listeners**********/
    public void onInfoClicked(){

        getNavigator().onInfoClicked();
    }

    public void onSettingsClicked(){

        getNavigator().onSettingsClicked();
    }

    public void onShareClicked(){

        getNavigator().onShareClicked();
    }

    public void onCreateNewNoteClicked(){

        getNavigator().onCreateNewNoteClicked();
    }

    public void onFilterClicked(){

        getNavigator().onFilterClicked();
    }

    public void hideCrmInfoDialog(){

        getNavigator().hideCrmInfoDialog();
    }

    public void onExportButtonClicked(){

        getNavigator().onExportButtonClicked();
    }

    /******DataModel Requests*********/
    public void requestAllOpportunities(CrmViewModel viewModel){

        dataModel.getAllOpportunities(viewModel);
    }

}
