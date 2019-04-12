package com.team.mamba.atlas.userInterface.welcome.select_business_account.business_login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BusinessLoginDataModel {


    private AppDataManager dataManager;


    @Inject
    public BusinessLoginDataModel(AppDataManager dataManager) {
        this.dataManager = dataManager;
    }



    /**
     * Query the DB to look for the email, if a match is found
     * login in as the business. If multiple are found a recyclerview
     * gives the user options on which business to represent
     *
     * @param email the business email address
     */
    public void authenticateCredentials(BusinessLoginViewModel viewModel, String email,String passWord) {

        //query database to look for the email
        //if match found login as the business
        //if multiple matched, show recylerview to select the business to represent

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);
                        List<BusinessProfile> verifiedProfileList = new ArrayList<>();

                        for (BusinessProfile profile: businessProfiles){
                            if (profile.getAdminPassword().equals(passWord)){
                                verifiedProfileList.add(profile);
                            }
                        }
                        viewModel.setBusinessProfileList(verifiedProfileList);

                        if (viewModel.getBusinessProfileList().isEmpty()) {
                            viewModel.getNavigator().showBusinessNotFoundAlert();

                        } else if (viewModel.getBusinessProfileList().size() == 1) {
                            updateBusinessProfile(viewModel, businessProfiles.get(0));

                        } else {
                            viewModel.getNavigator().showMultipleBusinessLogin();
                        }

                    } else {
                        String error = task.getException() != null ? task.getException().getLocalizedMessage()
                                : "Could not return list of businesses";
                        viewModel.getNavigator().handleError(error);
                    }
                });

    }

    private void updateBusinessProfile(BusinessLoginViewModel viewModel, BusinessProfile profile) {

        String userId = dataManager.getSharedPrefs().getUserId();
        dataManager.getSharedPrefs().setBusinessRepId(userId);

        if (!dataManager.getSharedPrefs().isBusinessAccount()) {
            profile.setBusinessRepId(userId);
        } else {
            String savedRepId = dataManager.getSharedPrefs().getBusinessRepId();
            profile.setBusinessRepId(savedRepId);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .document(profile.getId())
                .set(profile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        viewModel.getNavigator().openDashBoard();
                    } else {
                        String error = task.getException() != null ? task.getException().getLocalizedMessage()
                                : "Could not update business profile";
                        viewModel.getNavigator().handleError(error);
                    }
                });
    }
}
