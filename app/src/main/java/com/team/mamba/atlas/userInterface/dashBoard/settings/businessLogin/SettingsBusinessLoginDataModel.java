package com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_login.BusinessLoginViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SettingsBusinessLoginDataModel {


    private AppDataManager dataManager;

    @Inject
    public SettingsBusinessLoginDataModel(AppDataManager dataManager){
        this.dataManager = dataManager;
    }

    /**
     * Query the DB to look for the email, if a match is found
     * login in as the business. If multiple are found a recyclerview
     * gives the user options on which business to represent
     *
     */
    public void checkAllBusinesses(SettingsBusinessLoginViewModel viewModel) {

        //query database to look for the email
        //if match found login as the business
        //if multiple matched, show recylerview to select the business to represent

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("repId", id)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);
                        viewModel.setBusinessProfileList(businessProfiles);

                        if (viewModel.getBusinessProfileList().isEmpty()) {
                            viewModel.getNavigator().onEmptyBusinessesReturned();

                        } else if (viewModel.getBusinessProfileList().size() == 1) {

                            //todo open the dashboard with this as the business profile
//                            dataManager.getSharedPrefs().setUserId(businessProfiles.get(0).getId());
//                            viewModel.getNavigator().openDashBoard();

                        } else {
                            viewModel.getNavigator().onSuccessfulBusinessProfileResponse();
                        }

                    } else {
                        Logger.e(task.getException().getMessage());
                        viewModel.getNavigator().handleError(task.getException().getLocalizedMessage());
                    }
                });

    }
}
