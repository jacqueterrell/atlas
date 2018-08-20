package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.PersonalNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.List;
import javax.inject.Inject;

public class ContactNotesDataModel {


    private AppDataManager dataManager;


    @Inject
    public ContactNotesDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    /**
     * Look through our UserNotes DB and finds the record matching
     * the selected contact
     *
     * @param contactProfile the selected contact's profile
     */
    public void requestUserNotes(ContactNotesViewModel viewModel,UserProfile contactProfile){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.USER_NOTES_COLLECTION)
                .whereEqualTo("authorID",userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        List<PersonalNotes> personalNotesList = task.getResult().toObjects(PersonalNotes.class);

                        for (PersonalNotes notes : personalNotesList){

                            if (notes.getSubjectID().equals(contactProfile.getId())){

                                viewModel.setPersonalNotes(notes);

                            }
                        }

                        viewModel.getNavigator().onUserNotesReturned();

                    } else {

                        Logger.e(task.getException().getMessage());
                    }
                });

    }

}
