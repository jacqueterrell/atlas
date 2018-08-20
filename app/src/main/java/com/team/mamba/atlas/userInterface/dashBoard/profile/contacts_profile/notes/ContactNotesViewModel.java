package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import com.team.mamba.atlas.data.model.api.PersonalNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class ContactNotesViewModel extends BaseViewModel<ContactNotesNavigator> {

    private ContactNotesDataModel dataModel;
    PersonalNotes personalNotes;

    /*********************Getters and Setters********************/

    public void setDataModel(ContactNotesDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public PersonalNotes getPersonalNotes() {
        return personalNotes;
    }

    public void setPersonalNotes(PersonalNotes personalNotes) {
        this.personalNotes = personalNotes;
    }

    /*******************Onclick Listeners***********************/


    public void onEditDetailsClicked(){

        getNavigator().onEditDetailsClicked();
    }

    public void onConnectionInfoClicked(){

        getNavigator().onConnectionInfoClicked();
    }

    public void onNotesInfoClicked(){

        getNavigator().onNotesInfoClicked();
    }

    public void hideConnectionInfoDialog(){

        getNavigator().hideConnectionInfoDialog();
    }

    public void hidePersonalNotesInfoDialog(){

        getNavigator().hidePersonalNotesInfoDialog();
    }

    /*********************DataModel Requests**********************/

    public void requestUserNotes(ContactNotesViewModel viewModel,UserProfile profile) {

        dataModel.requestUserNotes(viewModel,profile);
    }
}
