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



}
