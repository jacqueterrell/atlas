package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

public class UserProfileDataModel {


    private AppDataManager dataManager;

    @Inject
    public UserProfileDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }



    public void getUserDetails(UserProfileViewModel viewModel, String userId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        if (!userProfiles.isEmpty()) {

                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }

}
