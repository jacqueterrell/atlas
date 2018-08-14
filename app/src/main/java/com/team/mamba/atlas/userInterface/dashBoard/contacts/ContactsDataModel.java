package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.crm.main.CrmViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactsDataModel {

    private AppDataManager dataManager;


    @Inject
    public ContactsDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }


    public void requestContactsInfo(ContactsViewModel viewModel) {

        viewModel.setSavedUserId(dataManager.getSharedPrefs().getUserId());
        requestUserProfiles(viewModel);
    }

    /**
     * Gets a list of all profiles and saves the user's profile
     */
    private void requestUserProfiles(ContactsViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> adjustedProfileList = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        for (UserProfile profile : userProfiles) {

                            if (profile.getId() != null) {

                                if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())) {

                                    viewModel.setUserProfile(profile);
                                }

                                adjustedProfileList.add(profile);
                            }
                        }

                        viewModel.setUserProfileList(adjustedProfileList);
                        requestAllConnections(viewModel);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task.getException().getMessage());

                    }

                });

    }


    /**
     * Gets a list of all of the user's approved connections
     */
    private void requestAllConnections(ContactsViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserConnections> userConnections = new ArrayList<>();

        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID", dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                        for (UserConnections connections : connectionsList) {

                            if (connections.isConfirmed && connections.getConsentingUserID().equals(userId)) {

                                userConnections.add(connections);

                            } else if (connections.isConfirmed && connections.getRequestingUserID().equals(userId)) {

                                userConnections.add(connections);
                            }

                        }

                        viewModel.setUserConnectionsList(userConnections);
                        requestAllBusinessProfiles(viewModel);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task.getException().getMessage());

                    }

                });

    }


    /**
     * Save a list of all business profiles and sets the user's business
     * profile if they are a business.
     */
    private void requestAllBusinessProfiles(ContactsViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String savedUserId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);

                        viewModel.setBusinessProfileList(businessProfiles);

                        for (BusinessProfile profile : businessProfiles) {

                            if (profile.getId().equals(savedUserId)) {

                                viewModel.setBusinessProfile(profile);

                            }
                        }

                        viewModel.getNavigator().onDataValuesReturned();

                    } else {

                        Logger.e(task.getException().getMessage());
                        viewModel.getNavigator().handleError("Error requesting business collections");
                    }
                });


    }


}
