package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_business;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * This class uses different queries based on the type account the
 * user is logged in as (individual or business) to add a business
 * profile
 *
 */
public class AddBusinessDataModel {


    private AppDataManager dataManager;


    @Inject
    public AddBusinessDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }


    public void addBusinessRequest(AddBusinessViewModel viewModel, String name, String code) {

        if (dataManager.getSharedPrefs().isBusinessAccount()) {

            getBusinessProfiles(viewModel,name,code);

        } else {

            getUserProfile(viewModel, name, code);

        }

    }


    /**
     * Gets the user profile and their contacts
     *
     * @param viewModel
     * @param name
     * @param code
     */
    public void getUserProfile(AddBusinessViewModel viewModel, String name, String code) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> idList = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("id", dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects((UserProfile.class));

                        UserProfile profile = userProfiles.get(0);


                        if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())) {

                            viewModel.setRequestingUserProfile(profile);

                            for (Map.Entry<String, String> entry : profile.getConnections().entrySet()) {//get a list of all current connections

                                String userId = entry.getKey();
                                idList.add(userId);
                            }

                        }

                        viewModel.setConnectionIdList(idList);
                        queryBusinessesForUserProfile(viewModel, name, code);

                    } else {

                        Logger.e(task.getException().getMessage());
                    }

                });


    }


    /**
     * Query all business accounts as a user.
     *
     * @param viewModel
     * @param name
     * @param code
     */
    public void queryBusinessesForUserProfile(AddBusinessViewModel viewModel, String name, String code) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<BusinessProfile> selectedProfileList = new ArrayList<>();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects((BusinessProfile.class));

                        for (BusinessProfile profile : businessProfiles) {

                            if (profile.getName().toLowerCase().trim().equals(name.toLowerCase().trim())
                                    && profile.getCode().equals(code)) {

                                selectedProfileList.add(profile);

                            }

                        }


                        if (selectedProfileList.isEmpty()) {//no business found

                            viewModel.getNavigator().showUserNotFoundAlert();

                        } else {

                            BusinessProfile selectedProfile = selectedProfileList.get(0);

                            if (viewModel.getConnectionIdList().contains(selectedProfile.getId())) {//already a contact

                                viewModel.getNavigator().showAlreadyAContactAlert();

                            } else {

                                addNewConnectionForIndividual(viewModel, selectedProfile);

                            }
                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                    }

                });
    }





    /**
     * Creates a new Connection for the User Request
     *
     * @param viewModel
     */
    private void addNewConnectionForIndividual(AddBusinessViewModel viewModel, BusinessProfile businessProfile) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db.collection(AppConstants.CONNECTIONS_COLLECTION).document();
        UserProfile requestingProfile = viewModel.getRequestingUserProfile();

        Long timeStamp = System.currentTimeMillis() / 1000;
        String requestingToken = FirebaseInstanceId.getInstance().getToken();
        String id = newUserRef.getId();

        String consentingName = businessProfile.getName();
        String requestingName = requestingProfile.getFirstName() + " " + requestingProfile.getLastName();

        UserConnections userConnections = new UserConnections();
        userConnections.setConnectionType(3);
        userConnections.setConsentingUserID(businessProfile.getId());
        userConnections.setConsentingUserName(consentingName);
        userConnections.setId(id);
        userConnections.setConfirmed(false);
        userConnections.setOrgBus(true);
        userConnections.setReqDeviceToken(requestingToken);
        userConnections.setRequestingUserName(requestingName);
        userConnections.setRequestingUserID(requestingProfile.getId());
        userConnections.setTimestamp(timeStamp);

        newUserRef.set(userConnections)
                .addOnSuccessListener(documentReference -> {

                    viewModel.getNavigator().onRequestSent();

                })
                .addOnFailureListener(e -> {

                    Logger.e(e.getMessage());
                });
    }


    /*         Logged in as a Business Account                */

    /**
     * Gets the user profile and their contacts : user is logged
     * in as a Business Account
     *
     * @param viewModel
     * @param name the business name
     * @param code the business code
     */
    public void getBusinessProfiles(AddBusinessViewModel viewModel, String name, String code) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> connectionIdList = new ArrayList<>();
        List<BusinessProfile> selectedProfileList = new ArrayList<>();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects((BusinessProfile.class));

                        for (BusinessProfile profile : businessProfiles) {

                            if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())) {

                                viewModel.setRequestingBusinessProfile(profile);

                                for (Map.Entry<String, String> entry : profile.getContacts().entrySet()) {//get a list of all current connections

                                    String userId = entry.getKey();
                                    connectionIdList.add(userId);
                                }

                            } else if (profile.getName().equals(name)
                                    && profile.getCode().equals(code)){ //get a list of businesses that match the user's query

                                selectedProfileList.add(profile);
                            }
                        }

                        if (selectedProfileList.isEmpty()){

                            viewModel.getNavigator().showUserNotFoundAlert();

                        } else {

                            BusinessProfile profile = selectedProfileList.get(0);

                            if (connectionIdList.contains(profile.getId())){//already a contact

                                viewModel.getNavigator().showAlreadyAContactAlert();

                            } else {

                                addNewConnectionForBusiness(viewModel,profile);
                            }
                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                    }

                });


    }


    /**
     * Creates a new Connection for the User Request
     *
     * @param viewModel
     */
    private void addNewConnectionForBusiness(AddBusinessViewModel viewModel, BusinessProfile businessProfile) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db.collection(AppConstants.CONNECTIONS_COLLECTION).document();
        BusinessProfile requestingProfile = viewModel.getRequestingBusinessProfile();

        Long timeStamp = System.currentTimeMillis() / 1000;
        String requestingToken = FirebaseInstanceId.getInstance().getToken();
        String id = newUserRef.getId();

        String consentingName = businessProfile.getName();
        String requestingName = requestingProfile.getName();

        UserConnections userConnections = new UserConnections();
        userConnections.setConnectionType(3);
        userConnections.setConsentingUserID(businessProfile.getId());
        userConnections.setConsentingUserName(consentingName);
        userConnections.setId(id);
        userConnections.setConfirmed(false);
        userConnections.setOrgBus(true);
        userConnections.setReqDeviceToken(requestingToken);
        userConnections.setRequestingUserName(requestingName);
        userConnections.setRequestingUserID(requestingProfile.getId());
        userConnections.setTimestamp(timeStamp);

        newUserRef.set(userConnections)
                .addOnSuccessListener(documentReference -> {

                    viewModel.getNavigator().onRequestSent();

                })
                .addOnFailureListener(e -> {

                    Logger.e(e.getMessage());
                });
    }
}
