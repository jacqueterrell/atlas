package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.utils.AppConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WelcomeDataModel {

    private AppDataManager dataManager;
    FirebaseFirestore db;


    @Inject
    public WelcomeDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }


    public void fireBaseVerifyPhoneNumber(WelcomeViewModel viewModel,String phoneNumber){

        int timeOut = 60;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                timeOut,
                TimeUnit.SECONDS,
                viewModel.getNavigator().getParentActivity(),
                viewModel.getNavigator().getPhoneCallBacks());
    }

    /**
     * Use the received Firebase credential to attempt a sign in
     *
     */
    public void signInWithPhoneAuthCredential(WelcomeViewModel viewModel) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(viewModel.getPhoneAuthCredential())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser user = task.getResult().getUser();
                        addUserToFirebaseDatabase(viewModel);
                        viewModel.getNavigator().openDashBoard();
                        Logger.d( "signInWithCredential:success");

                    } else {

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            viewModel.getNavigator().handleError("Invalid verification code");
                        }

                        Logger.e( "signInWithCredential:failure", task.getException());
                    }
                });

    }

    public void addUserToFirebaseDatabase(WelcomeViewModel viewModel){

        db = FirebaseFirestore.getInstance();

        //convert dob to time stamp
        Long timeStamp = System.currentTimeMillis() / 1000;

        Map<String, Object> user = new HashMap<>();
        user.put("firstName", "TestFirst");
        user.put("lastName", "TestLast");
        user.put("dob", 1815);
        user.put("phone","(217)-317-1667");
        user.put("id","");
        user.put("timestamp", timeStamp);

        db.collection(AppConstants.USERS_COLLECTION)
                .add(user)
                .addOnSuccessListener(documentReference -> {

                    String id = documentReference.getId();
                    viewModel.getNavigator().handleError("Upload successful");
                    dataManager.getSharedPrefs().setUserId(documentReference.getId());
             //       saveUserId();
                })
                .addOnFailureListener(e -> {
                    viewModel.getNavigator().handleError("Upload failed");
                    //
                });

    }

    private void saveUserId(){

        String userId = dataManager.getSharedPrefs().getUserId();

        DocumentReference reference = db.collection("users")
                .document(userId);

        reference
                .update("id",true)
                .addOnSuccessListener(documentReference -> {

                    Logger.d("successfully updated");
                })
                .addOnFailureListener(e -> {

                    Logger.d("failed to update");

                });

    }
}
