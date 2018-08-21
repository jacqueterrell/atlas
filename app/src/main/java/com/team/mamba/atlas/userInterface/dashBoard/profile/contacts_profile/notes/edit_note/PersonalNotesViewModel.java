package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes.edit_note;

import com.team.mamba.atlas.data.model.api.fireStore.PersonalNotes;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class PersonalNotesViewModel extends BaseViewModel<PersonalNoteNavigator> {

    private PersonalNoteDataModel dataModel;


    public void setDataModel(PersonalNoteDataModel dataModel) {
        this.dataModel = dataModel;
    }



    public void onFinishButtonClicked(){

        getNavigator().onFinishButtonClicked();
    }


    public void sendUserNote(PersonalNotesViewModel viewModel, PersonalNotes personalNotes){

        dataModel.sendUserNote(viewModel,personalNotes);
    }
}
