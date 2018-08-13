package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
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


    public void uploadImage(UserProfileViewModel viewModel,boolean isGallery,UserProfile profile){


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();


        if (isGallery){

            StorageReference galleryRef = storageRef.child("images/"+ dataManager.getSharedPrefs().getUserId());

            galleryRef.putFile(viewModel.getImageUri())
                    .addOnFailureListener(exception -> {
                        Logger.e("Upload of camera caputer failed");
                    }).addOnSuccessListener(taskSnapshot -> {

                Logger.i("download  url: " + storageRef.getDownloadUrl());
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            });


        } else {

            StorageReference galleryRef = storageRef.child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask = galleryRef.putFile(viewModel.getImageUri());


            galleryRef.putFile(viewModel.getImageUri())
                    .addOnFailureListener(exception -> {
                       Logger.e("Upload of camera caputer failed");
                    }).addOnSuccessListener(taskSnapshot -> {

                        Logger.i("download  url: " + storageRef.getDownloadUrl());
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    });

            storageRef.getDownloadUrl().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();
                    profile.setImageUrl(downloadUri.toString());

                } else {
                    // Handle failures
                    // ...
                    Logger.e("Error getting file");
                }
            });

        }

    }


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

}
