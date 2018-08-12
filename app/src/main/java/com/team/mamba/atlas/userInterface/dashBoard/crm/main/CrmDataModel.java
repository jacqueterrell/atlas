package com.team.mamba.atlas.userInterface.dashBoard.crm.main;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class CrmDataModel {

    private AppDataManager dataManager;

    @Inject
    public CrmDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void getAllOpportunities(CrmViewModel viewModel){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        getUserDetails(viewModel);

        db.collection(AppConstants.BUS_NOTES_COLLECTION)
                .whereEqualTo("authorID",dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<CrmNotes> crmNotes = task.getResult().toObjects(CrmNotes.class);

                        Collections.sort(crmNotes,(o1,o2) -> Long.compare(o2.getAdjustedTimeStamp(), o1.getAdjustedTimeStamp()));

                        viewModel.setCrmNotesList(crmNotes);
                        viewModel.getNavigator().onCrmDataReturned();

                    } else {

                        viewModel.getNavigator().handleError(task   .getException().getMessage());
                        Logger.e(task.getException().getMessage());
                    }

                });
    }


    /**
     * Gets a list of all profiles and saves the user's profile
     *
     * @param viewModel
     */
    private void getUserDetails(CrmViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> adjustedProfileList = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        for (UserProfile profile : userProfiles){

                            if (profile.getId() != null){

                                if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

                                    viewModel.setUserProfile(profile);
                                }

                                adjustedProfileList.add(profile);
                            }
                        }
                        getAllConnections(viewModel,adjustedProfileList);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task   .getException().getMessage());

                    }

                });

    }



    private void getAllConnections(CrmViewModel viewModel,List<UserProfile> userProfiles) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> usersContactProfiles = new ArrayList<>();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID",dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> connections = task.getResult().toObjects(UserConnections.class);

                        for (UserProfile profile : userProfiles){

                            for (UserConnections connections1 : connections){

                                if (profile.getId().equals(connections1.getConsentingUserID())){

                                    usersContactProfiles.add(profile);
                                }
                            }
                        }

                        viewModel.setUsersContactProfiles(usersContactProfiles);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task   .getException().getMessage());

                    }

                });

    }



}
