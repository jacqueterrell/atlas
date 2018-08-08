package com.team.mamba.atlas.userInterface.dashBoard.business_profile;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.userInterface.dashBoard.user_profile.UserProfileViewModel;
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
    public void getBusinessDetails(BusinessProfileViewModel viewModel){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String savedUserId = dataManager.getSharedPrefs().getUserId();

//        DocumentReference reference = db.collection(AppConstants.BUSINESSES_COLLECTION).document(savedUserId);
//        reference.get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()){
//
//                BusinessProfile profile = task.getResult().toObject(BusinessProfile.class);
//                viewModel.setBusinessProfile(profile);
//                viewModel.getNavigator().setBusinessDetails();
//
//            }else {
//
//                Logger.e(task.getException().getMessage());
//                task.getException().printStackTrace();
//            }
//        });

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("id", savedUserId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        BusinessProfile businessProfiles = task.getResult().toObjects(BusinessProfile.class).get(0);

                        viewModel.setBusinessProfile(businessProfiles);
                        viewModel.getNavigator().setBusinessDetails();

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }
}
