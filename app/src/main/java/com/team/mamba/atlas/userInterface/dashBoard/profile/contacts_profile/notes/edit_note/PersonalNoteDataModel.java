package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes.edit_note;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.PersonalNotes;
import com.team.mamba.atlas.utils.AppConstants;

import javax.inject.Inject;

public class PersonalNoteDataModel {

    private AppDataManager dataManager;


    @Inject
    public PersonalNoteDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void sendUserNote(PersonalNotesViewModel viewModel,PersonalNotes personalNotes){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newNotesRef = db.collection(AppConstants.USER_NOTES_COLLECTION).document();
        Long timeStamp = System.currentTimeMillis() / 1000;
        String id = newNotesRef.getId();

        personalNotes.setTimestamp(timeStamp);
        personalNotes.setId(id);

        newNotesRef.set(personalNotes)
                .addOnSuccessListener(aVoid -> {
                    viewModel.getNavigator().onNoteSentSuccessfully();
                })
                .addOnFailureListener(e -> {
                    String msg = "Error saving notes, please try again later";
                    viewModel.getNavigator().handleError(msg);
                });

    }
}
