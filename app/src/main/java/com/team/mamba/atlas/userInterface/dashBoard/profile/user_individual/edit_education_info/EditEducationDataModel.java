package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_education_info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;
import javax.inject.Inject;

public class EditEducationDataModel {

    private AppDataManager dataManager;


    @Inject
    public EditEducationDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void updateUser(EditEducationViewModel viewModel, UserProfile profile) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.USERS_COLLECTION)
                .document(profile.getId())
                .set(profile)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        viewModel.getNavigator().onProfileUpdated();

                    } else {

                        Logger.e("Could not update address: " + task.getException());
                    }

                });
    }
}
