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
    public ContactsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }



    public void requestContactsInfo(ContactsViewModel viewModel){

        requestUserProfiles(viewModel);
    }

    /**
     * Gets a list of all profiles and saves the user's profile
     *
     * @param viewModel
     */
    private void requestUserProfiles(ContactsViewModel viewModel) {

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
                        requestAllConnections(viewModel,adjustedProfileList);
                        requestAllBusinessProfiles(viewModel);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task.getException().getMessage());

                    }

                });

    }



    private void requestAllConnections(ContactsViewModel viewModel,List<UserProfile> userProfiles) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> usersContactProfiles = new ArrayList<>();
        List<UserConnections> userConnections = new ArrayList<>();

        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID",dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                        for (UserProfile profile : userProfiles){

                            for (UserConnections connections : connectionsList){

                                if (connections.isConfirmed && profile.getId().equals(connections.getConsentingUserID())){

                                    usersContactProfiles.add(profile);
                                    userConnections.add(connections);

                                } else if (connections.isConfirmed && connections.getRequestingUserID().equals(userId)){

                                    usersContactProfiles.add(profile);
                                    userConnections.add(connections);
                                }
                            }
                        }

                        viewModel.setUserConnectionsList(userConnections);
                        viewModel.setUserProfileList(usersContactProfiles);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                        viewModel.getNavigator().handleError(task   .getException().getMessage());

                    }

                });

    }



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

                    }
                });


    }


}
