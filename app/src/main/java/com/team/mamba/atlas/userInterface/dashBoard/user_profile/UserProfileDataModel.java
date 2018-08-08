package com.team.mamba.atlas.userInterface.dashBoard.user_profile;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.data.model.TestProfile;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoViewModel;
import com.team.mamba.atlas.utils.AppConstants;

import junit.framework.Test;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class UserProfileDataModel {


    private AppDataManager dataManager;

    @Inject
    public UserProfileDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }



    public void getUserDetails(UserProfileViewModel viewModel,String userId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        if (!userProfiles.isEmpty()) {
                            viewModel.setUserProfile(userProfiles.get(0));
                            viewModel.getNavigator().setUserDetails();
                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }

}
