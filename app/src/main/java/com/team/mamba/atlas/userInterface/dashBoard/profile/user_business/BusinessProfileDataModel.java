package com.team.mamba.atlas.userInterface.dashBoard.profile.user_business;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.utils.AppConstants;
import javax.inject.Inject;

public class BusinessProfileDataModel {

    AppDataManager dataManager;


    @Inject
    public BusinessProfileDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }

    /**
     * Retrieves the profile if the user signs in as a business
     * @param viewModel
     */
    public void getBusinessDetails(BusinessProfileViewModel viewModel, String businessId){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("id", businessId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        BusinessProfile businessProfiles = task.getResult().toObjects(BusinessProfile.class).get(0);

                        viewModel.setBusinessProfile(businessProfiles);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }
}
