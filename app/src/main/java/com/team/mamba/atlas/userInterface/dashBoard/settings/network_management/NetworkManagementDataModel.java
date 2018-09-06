package com.team.mamba.atlas.userInterface.dashBoard.settings.network_management;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class NetworkManagementDataModel {

    private AppDataManager dataManager;

    @Inject
    public NetworkManagementDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }


    /**
     * Gets the list of al UserProfiles from the DB
     */
    public void getUsersContacts(NetworkManagementViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> profileList = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        for (UserProfile profile : userProfiles){

                            if (profile.getId() != null){

                                if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())) {

                                    viewModel.setLoggedInUserProfile(profile);
                                }

                                profileList.add(profile);
                            }
                        }

                        getAllUsersContacts(viewModel, profileList);

                    } else {

                        viewModel.getNavigator().handleError(task.getException().getMessage());
                        Logger.e(task.getException().getMessage());
                    }
                });
    }


    /***
     * Gets a list of contacts filtered by the user being the requester
     *
     * @param userProfiles list of all user profiles
     */
    private void getAllUsersContacts(NetworkManagementViewModel viewModel, List<UserProfile> userProfiles) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserConnections> userConnections = new ArrayList<>();
        String userId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID", userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                        for (UserConnections connections : connectionsList) {

                            for (UserProfile profile : userProfiles) {

                                    if (connections.isConfirmed
                                            && connections.getConsentingUserID().equals(profile.getId())) {

                                        connections.setUserProfile(profile);
                                        userConnections.add(connections);
                                    }


                            }
                        }

                        //add all individual profiles to the list
                        getAllBusinesses(viewModel, userConnections,connectionsList);

                    } else {

                        viewModel.getNavigator().handleError(task.getException().getMessage());
                        Logger.e(task.getException().getMessage());
                    }
                });

    }


    /**
     * Queries all business profiles, finds the ones that match the 'consentingUserID'
     * field in the DB, and adds them to the contact list
     *
     * @param userConnections list of all individual confirmed connections
     * @param allConnectionsList list of all confirmed connections
     */
    private void getAllBusinesses(NetworkManagementViewModel viewModel,
                                  List<UserConnections> userConnections,
                                  List<UserConnections> allConnectionsList) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserConnections> filteredConnections = new ArrayList<>(userConnections);

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);


                        for (BusinessProfile profile : businessProfiles) {

                            if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

                                viewModel.setLoggedInBusinessProfile(profile);
                            }

                            for (UserConnections connection : allConnectionsList) {

                                if (connection.getConsentingUserID().equals(profile.getId())
                                        && connection.isConfirmed) {

                                    connection.setBusinessProfile(profile);
                                    filteredConnections.add(connection);
                                }

                            }
                        }


                        Collections.sort(filteredConnections,(o1, o2) -> Double.compare(o2.getTimestamp(), o1.getTimestamp()));

                        viewModel.setUserConnectionsList(filteredConnections);
                        viewModel.getNavigator().onContactListReceived();


                    } else {

                        Logger.e(task.getException().getMessage());
                        viewModel.getNavigator().handleError(task.getException().getMessage());

                    }
                });
    }


    /**
     * Deletes the Connection from the DB
     *
     * @param userConnection the selected connection
     */
    public void deleteUserConnection(NetworkManagementViewModel viewModel,UserConnections userConnection){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .document(userConnection.getId())
                .delete()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        Logger.i("Successfully deleted");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(userConnection.getConsentingUserID());
                        deleteFromContactList(viewModel,userConnection);

                    } else {

                        Logger.e("Failed to delete connection " + task.getException().getLocalizedMessage());
                    }
                });
    }


    /**
     * Deletes the Connection from the user's contact list
     *
     * @param connections the selected connection
     */
    private void deleteFromContactList(NetworkManagementViewModel viewModel,UserConnections connections){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (dataManager.getSharedPrefs().isBusinessAccount()){

            BusinessProfile profile = viewModel.getLoggedInBusinessProfile();
            Map<String,String> contactsMap = profile.getContacts();
            contactsMap.remove(connections.getConsentingUserID());

            db.collection(AppConstants.BUSINESSES_COLLECTION)
                    .document(profile.getId())
                    .set(profile)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Logger.i("Deleted contact successfully");

                        } else {

                            Logger.e(task.getException().getMessage());
                        }
                    });

        } else {

            UserProfile profile = viewModel.getLoggedInUserProfile();
            Map<String,String> contactsMap = profile.getConnections();
            contactsMap.remove(connections.getConsentingUserID());

            db.collection(AppConstants.USERS_COLLECTION)
                    .document(profile.getId())
                    .set(profile)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Logger.i("Deleted contact successfully");

                        } else {

                            Logger.e(task.getException().getMessage());
                        }
                    });

        }

    }
}
