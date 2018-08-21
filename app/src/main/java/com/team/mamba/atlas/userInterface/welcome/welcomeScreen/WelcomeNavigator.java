package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthProvider;

public interface WelcomeNavigator {

    void onStartButtonClicked();

    void onBusinessLoginClicked();

    void onDateVerifyClicked();

    void onDateCancelClicked();

    void onBackPressed();

    void handleError(String errorMsg);

    void showBusinessNotFoundAlert();

    void showMultipleBusinessLogin();

    void onBusinessScreenLoginClicked();

    void onBusinessScreenLearnMoreClicked();

    void openDashBoard();

    Activity getParentActivity();

}
