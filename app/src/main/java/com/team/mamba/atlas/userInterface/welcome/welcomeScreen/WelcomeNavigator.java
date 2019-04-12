package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthProvider;

public interface WelcomeNavigator {

    void onStartButtonClicked();

    void onDateVerifyClicked();

    void onDateCancelClicked();

    void onBackPressed();
}
