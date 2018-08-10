package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

public class CrmViewModel extends BaseViewModel<CrmNavigator> {

    private CrmDataModel dataModel;
    private static List<CrmNotes> crmNotesList = new ArrayList<>();
    private static List<UserProfile> usersContactProfiles = new ArrayList<>();


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
