package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
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
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeScreenLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.utils.CommonUtils;
import com.team.mamba.atlas.utils.formatData.RegEx;

import javax.inject.Inject;

import kotlin.text.Regex;

public class WelcomeFragment extends BaseFragment<WelcomeScreenLayoutBinding,WelcomeViewModel> implements WelcomeNavigator{

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeDataModel dataModel;

    private WelcomeScreenLayoutBinding binding;

    public static WelcomeFragment newInstance(){

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
        return null;
    }

    @Override
    public void onStartButtonClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);

    }

    @Override
    public void onBusinessLoginClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onDateVerifyClicked() {

        validateAge();
       // onDateCancelClicked();
    }

    @Override
    public void onDateCancelClicked() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.GONE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onFirstNameNextClicked() {

        String name = binding.dialogEnterFirstName.etFirstName.getText().toString();

        if (viewModel.isNameValid(name)){

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

        if (viewModel.isNameValid(name)){

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

        //todo send to firebase phone Auth
        String phoneNumber = binding.dialogEnterPhoneNumber.etPhoneNumber.getText().toString().replaceAll(RegEx.REMOVE_NON_DIGITS,"");

        if (CommonUtils.isPhoneValid(phoneNumber)){

            showPhoneNumberAlert(phoneNumber);

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
    public void onBackPressed() {

        if (binding.dialogEnterPhoneNumber.layoutPhoneNumber.getVisibility() == View.VISIBLE){

            hideEnterPhoneNumber();

        } else if (binding.dialogEnterLastName.layoutLastName.getVisibility() == View.VISIBLE){

            hideEnterLastName();

        } else if (binding.dialogEnterFirstName.layoutFirstName.getVisibility() == View.VISIBLE){

            hideEnterFirstName();

        } else if (binding.dialogVerifyAge.layoutVerifyAge.getVisibility() == View.VISIBLE){

            onDateCancelClicked();

        } else {

            getBaseActivity().onBackPressed();
        }
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


        return binding.getRoot();
    }

    private void validateAge(){

        int month =  (binding.dialogVerifyAge.datePicker.getMonth());
        int day = binding.dialogVerifyAge.datePicker.getDayOfMonth();
        int year = binding.dialogVerifyAge.datePicker.getYear();

        if (viewModel.isAgeValid(month,day,year)){

            showSnackbar("verified");
            onDateCancelClicked();
            showEnterFirstName();

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


    private void showEnterFirstName(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterFirstName.layoutFirstName.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterFirstName.layoutFirstName);

        binding.dialogEnterFirstName.etFirstName.requestFocus();
        showSoftKeyboard(binding.dialogEnterFirstName.etFirstName);

    }

    private void hideEnterFirstName(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterFirstName.layoutFirstName.setVisibility(View.GONE))
                .playOn(binding.dialogEnterFirstName.layoutFirstName);

    }

    private void showEnterLastName(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterLastName.layoutLastName.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterLastName.layoutLastName);

        binding.dialogEnterLastName.etLastName.requestFocus();
        showSoftKeyboard(binding.dialogEnterLastName.etLastName);
    }

    private void hideEnterLastName(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterLastName.layoutLastName.setVisibility(View.GONE))
                .playOn(binding.dialogEnterLastName.layoutLastName);
    }

    private void showEnterPhoneNumber(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogEnterPhoneNumber.layoutPhoneNumber.setVisibility(View.VISIBLE))
                .playOn(binding.dialogEnterPhoneNumber.layoutPhoneNumber);

        binding.dialogEnterPhoneNumber.etPhoneNumber.requestFocus();
        showSoftKeyboard(binding.dialogEnterPhoneNumber.etPhoneNumber);
    }

    private void hideEnterPhoneNumber(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogEnterPhoneNumber.layoutPhoneNumber.setVisibility(View.GONE))
                .playOn(binding.dialogEnterPhoneNumber.layoutPhoneNumber);
    }


    private void showPhoneNumberAlert(String phoneNumber){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        dialog.setTitle(R.string.phone_number_warning_title)
                .setMessage(R.string.phone_number_warning_body)
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Continue",(paramDialogInterface, paramInt) -> {

                    //Todo: send to Firebase phone Auth
                });

        dialog.show();
    }

}
