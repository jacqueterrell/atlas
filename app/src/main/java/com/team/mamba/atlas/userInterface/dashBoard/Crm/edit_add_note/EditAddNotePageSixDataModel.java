package com.team.mamba.atlas.userInterface.dashBoard.Crm.edit_add_note;

import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.CrmNotes;

import javax.inject.Inject;

public class EditAddNotePageSixDataModel {


    private AppDataManager dataManager;

    @Inject
    public EditAddNotePageSixDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void setCrmNote(EditAddNotePageSixViewModel viewModel, CrmNotes notes){



    }
}
