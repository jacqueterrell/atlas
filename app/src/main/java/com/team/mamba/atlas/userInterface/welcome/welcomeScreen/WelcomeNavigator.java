package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthProvider;

public interface WelcomeNavigator {

    void onStartButtonClicked();

    void onBusinessLoginClicked();

    void onDateVerifyClicked();

    void onDateCancelClicked();

    void onFirstNameNextClicked();

    void onFirstNamePreviousClicked();

    void onLastNameNextClicked();

    void onLastNamePreviousClicked();

    void onPhoneSubmitClicked();

    void onPhoneSubmitPreviousClicked();

    void onEnterSmsCancelClicked();

    void onEnterSmsContinueClicked();

    void onBackPressed();

    void handleError(String errorMsg);

    void openDashBoard();

    void loginAsOneBusiness();

    void showBusinessNotFoundAlert();

    void showMultipleBusinessLogin();

    void onBusinessScreenLoginClicked();

    void onBusinessScreenLearnMoreClicked();


    Activity getParentActivity();

    PhoneAuthProvider.OnVerificationStateChangedCallbacks getPhoneCallBacks();

}
