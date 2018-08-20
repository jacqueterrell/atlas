package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.List;
import javax.inject.Inject;

public class ContactProfileDataModel {

    private AppDataManager dataManager;


    @Inject
    public ContactProfileDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void getConnectionType(ContactProfileViewModel viewModel,UserProfile profile){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("requestingUserID",profile.getId())
                .get()
                .addOnCompleteListener(task -> {

                    List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                    for (UserConnections connection : connectionsList){

                        if (connection.getConsentingUserID().equals(dataManager.getSharedPrefs().getUserId())){

                            profile.setConnectionType(connection.getConnectionType());
                            viewModel.setConsentingProfile(profile);

                        }
                    }

                    viewModel.getNavigator().onConnectionTypeSaved();
                });
    }
}
