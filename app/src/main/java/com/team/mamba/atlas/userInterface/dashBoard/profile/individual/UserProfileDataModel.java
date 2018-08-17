package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_phone_info.EditPhoneViewModel;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.List;
import java.util.UUID;

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


    /**
     * Uploads our user's image to our Firebase storage
     *
     * @param viewModel
     * @param profile
     */
    public void uploadImage(UserProfileViewModel viewModel,UserProfile profile){


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference galleryRef = storageRef.child("images/"+ dataManager.getSharedPrefs().getUserId());

        galleryRef.putFile(viewModel.getImageUri())
                .addOnSuccessListener(taskSnapshot -> galleryRef.getDownloadUrl().addOnCompleteListener(task -> {

                    Logger.i("Successful " + task.getResult());
                    profile.setImageUrl(task.getResult().toString());
                    updateUser(profile);
                }))
                .addOnFailureListener(e -> Logger.e("failed"));

    }


    /**
     * Updates the user's image to the new link
     *
     * @param profile
     */
    public void updateUser(UserProfile profile) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.USERS_COLLECTION)
                .document(profile.getId())
                .set(profile)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Logger.i("Image uploaded successfully");

                    } else {

                        Logger.e("Could not update address: " + task.getException());
                    }

                });
    }


    public void getConnectionType(UserProfileViewModel viewModel,UserProfile profile){

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
