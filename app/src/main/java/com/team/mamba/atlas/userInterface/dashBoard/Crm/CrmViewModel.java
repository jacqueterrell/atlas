package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

public class CrmViewModel extends BaseViewModel<CrmNavigator> {

    private CrmDataModel dataModel;
    private static List<CrmNotes> crmNotesList = new ArrayList<>();


    public void setDataModel(CrmDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setCrmNotesList(List<CrmNotes> crmNotesList) {
        CrmViewModel.crmNotesList = crmNotesList;
    }

    public List<CrmNotes> getCrmNotesList() {
        return crmNotesList;
    }

    /******DataModel Requests*********/
    public void requestAllOpportunites(CrmViewModel viewModel){

        dataModel.getAllOpportunities(viewModel);
    }
}
