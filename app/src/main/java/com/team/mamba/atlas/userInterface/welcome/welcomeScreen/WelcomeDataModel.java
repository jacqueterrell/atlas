package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.List;
import javax.inject.Inject;

public class WelcomeDataModel {

    private AppDataManager dataManager;

    @Inject
    public WelcomeDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }



    public void firebaseAuthenticateByEmail(WelcomeViewModel viewModel, String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(viewModel.getNavigator().getParentActivity(), task -> {

                    if (task.isSuccessful()) {

                        checkAllBusinesses(viewModel, email);

                    } else {

                        viewModel.getNavigator().showBusinessNotFoundAlert();
                    }
                });

    }

    public void checkAllBusinesses(WelcomeViewModel viewModel, String email) {

        //query database to look for the email
        //if match found login as the business
        //if multiple matched recylerview to select the business to represent

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);

                        viewModel.setBusinessProfileList(businessProfiles);

                        if (viewModel.getBusinessProfileList().isEmpty()) {

                            //todo can this situation exist
                            viewModel.getNavigator().showBusinessNotFoundAlert();

                        } else if (viewModel.getBusinessProfileList().size() == 1) {

                            dataManager.getSharedPrefs().setUserId(businessProfiles.get(0).getId());
                            viewModel.getNavigator().openDashBoard();

                        } else {

                            viewModel.getNavigator().showMultipleBusinessLogin();
                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }
                });

    }


}
