package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;
import javax.inject.Inject;

public class EditEmailDataModel {

    private AppDataManager dataManager;


    @Inject
    public EditEmailDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void updateUser(EditEmailViewModel viewModel,UserProfile profile){

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
