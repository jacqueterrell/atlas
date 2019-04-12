package com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.utils.AppConstants;
import javax.inject.Inject;

public class BusinessAccountsDataModel {

    private AppDataManager dataManager;


    @Inject
    public BusinessAccountsDataModel(AppDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void updateBusinessProfile(BusinessAccountsViewModel viewModel, BusinessProfile profile) {

        if (dataManager.getSharedPrefs().getUserId().isEmpty()) {
            viewModel.getNavigator().showCreateUserAccountAlert();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = dataManager.getSharedPrefs().getUserId();
        dataManager.getSharedPrefs().setBusinessRepId(userId);

        /*
        We do this because the busineRepId must be a user's id.  Logging in as a business overwrites
        the sharedPref local userId as tbe business's profile id. In this case, use the sharedPref
        local repId as the selected business profile's rep id
         */
        if (!dataManager.getSharedPrefs().isBusinessAccount()) {
            profile.setBusinessRepId(userId);

        } else {
            String savedRepId = dataManager.getSharedPrefs().getBusinessRepId();
            profile.setBusinessRepId(savedRepId);
        }

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .document(profile.getId())
                .set(profile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        viewModel.getNavigator().onAccountUpdatedSuccessfully(profile);
                    } else {
                        String error = task.getException() != null ? task.getException().getLocalizedMessage() : "Could not update business profile";
                        viewModel.getNavigator().handleError(error);
                    }
                });
    }
}
