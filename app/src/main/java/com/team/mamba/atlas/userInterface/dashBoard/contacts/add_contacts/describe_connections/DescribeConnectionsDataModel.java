package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;

import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_user.AddUserViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class DescribeConnectionsDataModel {

    private AppDataManager dataManager;

    @Inject
    public DescribeConnectionsDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void addNewRequest(DescribeConnectionsViewModel viewModel,String lastName,String code,int connectionType){
        //query users and search for a match...

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<UserProfile> selectedProfileList = new ArrayList<>();

        List<String> connectionIdList = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);


                    for (UserProfile profile : userProfiles){

                        if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

                            viewModel.setLoggedInProfile(profile);

                            for (Map.Entry<String,String> entry : profile.getConnections().entrySet()){//get a list of all current connections

                                String userId = entry.getKey();
                                connectionIdList.add(userId);
                            }

                        } else if (profile.getLastName().toLowerCase().equals(lastName.toLowerCase().trim())
                                && profile.getCode().equals(code)){

                            selectedProfileList.add(profile);
                        }
                    }


                    if (selectedProfileList.isEmpty()){//no user found

                        viewModel.getNavigator().showUserNotFoundAlert();

                    } else {

                        UserProfile selectedProfile = selectedProfileList.get(0);

                       if (connectionIdList.contains(selectedProfile.getId())){//already a contact

                           viewModel.getNavigator().showAlreadyAContactAlert();

                       } else {

                           addNewConnection(viewModel,selectedProfile,connectionType);
                       }
                    }
                });
    }


    /**
     * Creates a new Connection for the User Request
     *
     * @param viewModel
     * @param consentingProfile
     */
    private void addNewConnection(DescribeConnectionsViewModel viewModel, UserProfile consentingProfile,int connectionType){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db.collection(AppConstants.CONNECTIONS_COLLECTION).document();
        UserProfile requestingProfile = viewModel.getLoggedInProfile();

        Long timeStamp = System.currentTimeMillis() / 1000;
        String requestingToken = FirebaseInstanceId.getInstance().getToken();
        String id = newUserRef.getId();

        String consentingName = consentingProfile.getFirstName() + " " + consentingProfile.getLastName();
        String requestingName = requestingProfile.getFirstName() + " " + requestingProfile.getLastName();

        UserConnections userConnections = new UserConnections();
        userConnections.setConDeviceToken(consentingProfile.getDeviceToken());
        userConnections.setConnectionType(connectionType);
        userConnections.setConsentingUserID(consentingProfile.getId());
        userConnections.setConsentingUserName(consentingName);
        userConnections.setId(id);
        userConnections.setConfirmed(false);
        userConnections.setReqDeviceToken(requestingToken);
        userConnections.setRequestingUserName(requestingName);
        userConnections.setTimestamp(timeStamp);

        newUserRef.set(userConnections)
                .addOnSuccessListener(documentReference -> {

                    viewModel.getNavigator().onRequestSent();

                })
                .addOnFailureListener(e -> {

                    Logger.e(e.getMessage());
                });
    }
}
