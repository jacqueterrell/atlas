package com.team.mamba.atlas.userInterface.welcome.welcomeScreen.enter_phone_number;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_phone_info.EditPhoneViewModel;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.formatData.AppFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class EnterPhoneDataModel {


    private AppDataManager dataManager;
    private boolean isUser = false;


    @Inject public EnterPhoneDataModel(AppDataManager dataManager){

        this.dataManager = dataManager;
    }


    public void fireBaseVerifyPhoneNumber(EnterPhoneViewModel viewModel, String phoneNumber) {

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
     */
    public void signInWithPhoneAuthCredential(EnterPhoneViewModel viewModel) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(viewModel.getPhoneAuthCredential())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        saveUser(viewModel);
                        Logger.d("signInWithCredential:success");

                    } else {

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            viewModel.getNavigator().handleError("Invalid verification code");

                        }

                        Logger.e("signInWithCredential:failure", task.getException());
                    }
                });

    }

    /**
     * Checks to see if the user's details have already been saved
     */
    private void saveUser(EnterPhoneViewModel viewModel) {

        String phone = viewModel.getPhoneNumber();
        String firstName = viewModel.getFirstName();
        String lastName = viewModel.getLastName();
        String dateOfBirth = Long.toString(viewModel.getDateOfBirth());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("firstName", firstName)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String userId = document.getData().get("id").toString();
                            String userCode = document.getData().get("code").toString();
                            String last = document.getData().get("lastName").toString();
                            String phoneNumber = document.getData().get("phone").toString();
                            String dob = document.getData().get("dob").toString();

                            String adjustedDob = AppFormatter.timeStampFormatter.format(Double.valueOf(dob));

                            if (last.equals(lastName)
                                    && phoneNumber.equals(phone)
                                    && adjustedDob.equals(dateOfBirth)) {

                                isUser = true;

                                dataManager.getSharedPrefs().setUserId(userId);
                                dataManager.getSharedPrefs().setUserCode(userCode);
                                break;
                            }
                        }

                        if (isUser) {

                            viewModel.getNavigator().openDashBoard();

                        } else {

                            addUserToFirebaseDatabase(viewModel);
                        }

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }



    public void addUserToFirebaseDatabase(EnterPhoneViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //convert dob to time stamp
        Long timeStamp = System.currentTimeMillis() / 1000;
        String token = FirebaseInstanceId.getInstance().getToken();
        DocumentReference newUserRef = db.collection(AppConstants.USERS_COLLECTION).document();
        String myId = newUserRef.getId();
        String userCode = "2";

        Map<String, Object> user = new HashMap<>();
        user.put("firstName", viewModel.getFirstName());
        user.put("lastName", viewModel.getLastName());
        user.put("dob", viewModel.getDateOfBirth());
        user.put("phone", viewModel.getPhoneNumber());
        user.put("timestamp", timeStamp);
        user.put("deviceToken", token);
        user.put("id", myId);
        user.put("badgeCount", 0);
        user.put("code", userCode);
        user.put("score", 0);

        newUserRef.set(user)
                .addOnSuccessListener(documentReference -> {

                    dataManager.getSharedPrefs().setUserId(myId);
                    dataManager.getSharedPrefs().setUserCode(userCode);

                    viewModel.getNavigator().openDashBoard();

                })
                .addOnFailureListener(e -> {
                    viewModel.getNavigator().handleError("Upload failed");
                    //
                });

    }
}
