package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeScreenLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.utils.CommonUtils;
import com.team.mamba.atlas.utils.formatData.RegEx;

import javax.inject.Inject;


public class WelcomeFragment extends BaseFragment<WelcomeScreenLayoutBinding, WelcomeViewModel> implements WelcomeNavigator {

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeDataModel dataModel;

    private WelcomeScreenLayoutBinding binding;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private static final int DEFAULT_TIME_IN_MILLI_SECS = 500;

    public static WelcomeFragment newInstance() {

        return new WelcomeFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_screen_layout;
    }

    @Override
    public WelcomeViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
    }

    @Override
    public void onStartButtonClicked() {

        viewModel.setBusinessLogin(false);

        YoYo.with(Techniques.FadeIn)
                .duration(DEFAULT_TIME_IN_MILLI_SECS)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);

    }

    @Override
    public void onBusinessLoginClicked() {

        viewModel.setBusinessLogin(true);

        YoYo.with(Techniques.FadeIn)
                .duration(DEFAULT_TIME_IN_MILLI_SECS)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onDateVerifyClicked() {

        validateAge();
    }

    @Override
    public void onDateCancelClicked() {

        YoYo.with(Techniques.FadeOut)
                .duration(DEFAULT_TIME_IN_MILLI_SECS)
                .onEnd(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.GONE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onFirstNameNextClicked() {

        String name = binding.dialogEnterFirstName.etFirstName.getText().toString();

        if (viewModel.isNameValid(name)) {

            viewModel.setFirstName(name);
            showEnterLastName();

        } else {

            showSnackbar("Please enter a valid first name");
        }
    }

    @Override
    public void onFirstNamePreviousClicked() {

        hideEnterFirstName();

    }

    @Override
    public void onLastNameNextClicked() {

        String name = binding.dialogEnterLastName.etLastName.getText().toString();

        if (viewModel.isNameValid(name)) {

            viewModel.setLastName(name);
            showEnterPhoneNumber();

        } else {

            showSnackbar("Please enter a valid last name");
        }
    }

    @Override
    public void onLastNamePreviousClicked() {

        hideEnterLastName();
        showEnterFirstName();
    }

    @Override
    public void onPhoneSubmitClicked() {

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String phoneNumber = binding.dialogEnterPhoneNumber.etPhoneNumber.getText().toString().replaceAll(RegEx.REMOVE_NON_DIGITS, "");

        if (CommonUtils.isPhoneValid(phoneNumber)) {

            try {

                Phonenumber.PhoneNumber pn = phoneNumberUtil.parse(phoneNumber, "US");
                String pnE164 = phoneNumberUtil.format(pn,PhoneNumberUtil.PhoneNumberFormat.E164);
                String international = phoneNumberUtil.format(pn, PhoneNumberFormat.INTERNATIONAL);
                String national = phoneNumberUtil.format(pn, PhoneNumberFormat.NATIONAL);

                viewModel.setPhoneNumber(national);
                showPhoneNumberAlert(pnE164);

            } catch (NumberParseException e) {
                e.printStackTrace();
            }

        } else {

            showSnackbar("Phone Num is not valid");
        }

    }

    @Override
    public void onPhoneSubmitPreviousClicked() {

        hideProgressSpinner();
        showEnterLastName();
    }

    @Override
    public void onEnterSmsCancelClicked() {

        hideEnterSMSCode();
    }

    @Override
    public void onEnterSmsContinueClicked() {

        String code = binding.etSmsCode.getText().toString();

        if (code.isEmpty()){

            showSnackbar("Your sms code is empty");

        } else {

            String verificationId = viewModel.getVerificationId();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            viewModel.setPhoneAuthCredential(credential);
            viewModel.signInWithPhoneAuthCredential(getViewModel());

            showProgressSpinner();
        }

    }

    @Override
    public Activity getParentActivity() {
        return getBaseActivity();
    }

    @Override
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks getPhoneCallBacks() {
        return mCallbacks;
    }

    @Override
    public void onBackPressed() {

        if (binding.dialogEnterPhoneNumber.layoutPhoneNumber.getVisibility() == View.VISIBLE) {

            hideEnterPhoneNumber();

        } else if (binding.dialogEnterLastName.layoutLastName.getVisibility() == View.VISIBLE) {

            hideEnterLastName();

        } else if (binding.dialogEnterFirstName.layoutFirstName.getVisibility() == View.VISIBLE) {

            hideEnterFirstName();

        } else if (binding.dialogVerifyAge.layoutVerifyAge.getVisibility() == View.VISIBLE) {

            onDateCancelClicked();

        } else {

            getBaseActivity().onBackPressed();
        }
    }

    @Override
    public void onBusinessScreenLearnMoreClicked() {

        //todo open website
    }

    @Override
    public void onBusinessScreenLoginClicked() {

        String email = binding.dialogBusinessLogin.etEmail.getText().toString();
        String password = binding.dialogBusinessLogin.etPassword.getText().toString();

        if (viewModel.isEmailAndPasswordValid(email,password)){

           viewModel.checkAllBusinesses(getViewModel());

        } else {

            showSnackbar("Please enter a valid email and password");
        }

    }

    @Override
    public void loginAsOneBusiness() {

        //todo open the dashboard as a business profile
    }

    @Override
    public void showMultipleBusinessLogin() {

        //todo show dialog fragment recyclerview
    }

    @Override
    public void openDashBoard() {

        dataManager.getSharedPrefs().setFirstName(viewModel.getFirstName());
        dataManager.getSharedPrefs().setLastName(viewModel.getFirstName());
        dataManager.getSharedPrefs().setPhoneNumber(viewModel.getPhoneNumber());


        hideEnterSMSCode();
        hideProgressSpinner();
       showAlert("Success","Dashboard is open");

    }

    @Override
    public void handleError(String errorMsg) {

        hideProgressSpinner();
        showSnackbar(errorMsg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = getViewDataBinding();

        Glide.with(getBaseActivity())
                .load(R.drawable.welcome_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(binding.imgViewBackground);

        Glide.with(getBaseActivity())
                .load(R.drawable.welcome_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(binding.dialogEnterFirstName.imgViewBackground);

        Glide.with(getBaseActivity())
                .load(R.drawable.welcome_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(binding.dialogEnterLastName.imgViewBackground);

        Glide.with(getBaseActivity())
                .load(R.drawable.welcome_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(binding.dialogEnterPhoneNumber.imgViewBackground);

        binding.dialogEnterPhoneNumber.etPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        setUpCallBacks();

        return binding.getRoot();
    }



    private void validateAge() {

        int month = (binding.dialogVerifyAge.datePicker.getMonth());
        int day = binding.dialogVerifyAge.datePicker.getDayOfMonth();
        int year = binding.dialogVerifyAge.datePicker.getYear();


        if (viewModel.isAgeValid(month, day, year)) {

            onDateCancelClicked();

            if (viewModel.isBusinessLogin()){

                showBusinessLoginScreen();

            } else {

                showEnterFirstName();

            }

        } else {

            showSnackbar("not verified");
        }

    }

    private void showSoftKeyboard(View view) {

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }


    private void showEnterFirstName() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterFirstName.layoutFirstName.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterFirstName.layoutFirstName);

        binding.dialogEnterFirstName.etFirstName.requestFocus();
        showSoftKeyboard(binding.dialogEnterFirstName.etFirstName);

    }

    private void hideEnterFirstName() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterFirstName.layoutFirstName.setVisibility(View.GONE))
                .playOn(binding.dialogEnterFirstName.layoutFirstName);

    }

    private void showEnterLastName() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterLastName.layoutLastName.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterLastName.layoutLastName);

        binding.dialogEnterLastName.etLastName.requestFocus();
        showSoftKeyboard(binding.dialogEnterLastName.etLastName);
    }

    private void hideEnterLastName() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterLastName.layoutLastName.setVisibility(View.GONE))
                .playOn(binding.dialogEnterLastName.layoutLastName);
    }

    private void showEnterPhoneNumber() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterPhoneNumber.layoutPhoneNumber.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterPhoneNumber.layoutPhoneNumber);

        binding.dialogEnterPhoneNumber.etPhoneNumber.requestFocus();
        showSoftKeyboard(binding.dialogEnterPhoneNumber.etPhoneNumber);
    }

    private void hideEnterPhoneNumber() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterPhoneNumber.layoutPhoneNumber.setVisibility(View.GONE))
                .playOn(binding.dialogEnterPhoneNumber.layoutPhoneNumber);
    }

    private void showEnterSMSCode(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.layoutEnterSmsCode.setVisibility(View.VISIBLE))
                .playOn(binding.layoutEnterSmsCode);

        binding.etSmsCode.setText("");

    }

    private void hideEnterSMSCode(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.layoutEnterSmsCode.setVisibility(View.GONE))
                .playOn(binding.layoutEnterSmsCode);
    }

    private void showBusinessLoginScreen(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.layoutEnterSmsCode.setVisibility(View.GONE))
                .playOn(binding.layoutEnterSmsCode);
    }

    private void hideBusinessLoginScreen(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.layoutEnterSmsCode.setVisibility(View.GONE))
                .playOn(binding.layoutEnterSmsCode);
    }

    /**
     * Shows an alert to validate the phone number
     *
     * @param phoneNumber the entered phone number
     */
    private void showPhoneNumberAlert(String phoneNumber) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        dialog.setTitle(R.string.phone_number_warning_title)
                .setMessage(R.string.phone_number_warning_body)
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Continue", (paramDialogInterface, paramInt) -> {

                   viewModel.fireBaseVerifyPhoneNumber(getViewModel(),phoneNumber);
                   showProgressSpinner();


                });

        dialog.show();
    }

    private void setUpCallBacks() {



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Logger.d("onVerificationCompleted: " + phoneAuthCredential);
                viewModel.setPhoneAuthCredential(phoneAuthCredential);
                viewModel.signInWithPhoneAuthCredential(getViewModel());

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Logger.e(e.getLocalizedMessage());
                hideProgressSpinner();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // the phone number format is not valid
                    // ...
                    showSnackbar("invalid credentials");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    showSnackbar("sms quota filled");

                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                viewModel.setVerificationId(verificationId);
                viewModel.setForceResendingToken(token);

                hideEnterPhoneNumber();
                hideEnterLastName();
                hideEnterFirstName();

                showEnterSMSCode();
                hideProgressSpinner();

                Logger.i("onCodeSent: " + verificationId);
            }
        };
    }

}
