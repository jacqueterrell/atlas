package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.PersonalNotes;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

public class ContactNotesDataModel {


    private AppDataManager dataManager;


    @Inject
    public ContactNotesDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void getConnectionType(ContactNotesViewModel viewModel,UserProfile contactProfile){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID",userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                        for (UserConnections connection : connectionsList){

                            if (connection.getConsentingUserID().equals(contactProfile.getId())){

                              //  viewModel.setConsentingProfile(profile);
                                viewModel.setYourConnectionType(connection.getConnectionType());
                                viewModel.setTimestamp(connection.getTimestamp());
                            }
                        }

                        requestUserNotes(viewModel,contactProfile);

                    } else {

                        Logger.e(task.getException().getMessage());
                    }
                });

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
        List<PersonalNotes>receivedNotes = new ArrayList<>();

        db.collection(AppConstants.USER_NOTES_COLLECTION)
                .whereEqualTo("authorID",userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        List<PersonalNotes> personalNotesList = task.getResult().toObjects(PersonalNotes.class);

                        for (PersonalNotes notes : personalNotesList){

                            if (notes.getSubjectID().equals(contactProfile.getId())){

                                receivedNotes.add(notes);

                            }
                        }

                        Collections.sort(receivedNotes,(o1,o2) -> Double.compare(o2.getTimestamp(), o1.getTimestamp()));

                        viewModel.setPersonalNotes(receivedNotes.get(0));
                        viewModel.getNavigator().onUserNotesReturned();

                    } else {

                        Logger.e(task.getException().getMessage());
                    }
                });

    }

}
