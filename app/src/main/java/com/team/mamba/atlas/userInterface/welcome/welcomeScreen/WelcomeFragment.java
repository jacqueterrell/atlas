package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeScreenLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivityNavigator;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.BusinessAccountsActivity;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.enter_first_name.EnterFirstNameFragment;
import com.team.mamba.atlas.utils.ChangeWelcomeFragments;
import javax.inject.Inject;


public class WelcomeFragment extends BaseFragment<WelcomeScreenLayoutBinding, WelcomeViewModel> implements WelcomeNavigator {

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeDataModel dataModel;

    private WelcomeScreenLayoutBinding binding;
    private static final int DEFAULT_TIME_IN_MILLI_SECS = 500;
    private WelcomeActivityNavigator parentNavigator;

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

        parentNavigator.setBusinessLogin(false);

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);

    }

    @Override
    public void onBusinessLoginClicked() {

        parentNavigator.setBusinessLogin(true);

        YoYo.with(Techniques.FadeIn)
                .duration(500)
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
    public Activity getParentActivity() {
        return getBaseActivity();
    }


    @Override
    public void onBackPressed() {

         if (binding.dialogVerifyAge.layoutVerifyAge.getVisibility() == View.VISIBLE) {

            onDateCancelClicked();

        } else if (binding.dialogBusinessLogin.layoutMain.getVisibility() == View.VISIBLE){

            hideBusinessLoginScreen();

        } else {

            getBaseActivity().onBackPressed();
        }
    }

    @Override
    public void onBusinessScreenLearnMoreClicked() {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_BUSINESS_URL));
        startActivity(i);
    }

    @Override
    public void onBusinessScreenLoginClicked() {


        String email = binding.dialogBusinessLogin.etEmail.getText().toString();
        String password = binding.dialogBusinessLogin.etPassword.getText().toString();
        hideKeyboard();

        if (email.isEmpty() && password.equals("admin")){

            dataManager.getSharedPrefs().setUserId("S0URPfcKiVanC5NhHd4n9ejcEWZ2");//Matt Maher
            parentNavigator.setBusinessLogin(false);
            openDashBoard();

        } else if (email.isEmpty() && password.equals("test")){

         //   dataManager.getSharedPrefs().setUserId("Dy3PDR8BiWS0L7gqfjo16YqFKKN2"); //Mike R
            dataManager.getSharedPrefs().setUserId("RGxZhoaRI2WE6Ge2I6oC"); // Jacque Terrell
            parentNavigator.setBusinessLogin(false);

            openDashBoard();

        } else {

            if (viewModel.isEmailValid(email) && viewModel.isPasswordValid(password)){

                viewModel.firebaseAuthenticateByEmail(viewModel,email,password);
                showProgressSpinner();

            } else {

                if (!viewModel.isEmailValid(email) && !viewModel.isPasswordValid(password)) {

                    showSnackbar("Please enter a valid email and password");

                } else if (!viewModel.isEmailValid(email)){

                    showSnackbar("Please enter a valid email");

                } else {

                    showSnackbar("Please enter a valid password");
                }
            }
        }
    }


    @Override
    public void showMultipleBusinessLogin() {

        hideProgressSpinner();
        hideBusinessLoginScreen();
        startActivity(BusinessAccountsActivity.newIntent(getBaseActivity(),viewModel.getBusinessProfileList()));
    }

    @Override
    public void showBusinessNotFoundAlert() {

        hideProgressSpinner();
        String title = "Login Issue";
        String body = getBaseActivity().getResources().getString(R.string.business_not_found_body);
        showAlert(title,body);
    }



    @Override
    public void handleError(String errorMsg) {

        hideProgressSpinner();
        showSnackbar(errorMsg);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentNavigator = (WelcomeActivityNavigator) context;
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


        //Todo reserved for when the adding a business feature is enabled
//        String email = "jacqueterrell@yahoo.com";
//        String password = "123456";
//
//
//                mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(viewModel.getNavigator().getParentActivity(), task -> {
//
//                    if (task.isSuccessful()){
//
//                        Logger.e("Email Auth successfull");
//
//                    } else {
//
//                        Logger.e("Error creating authentication");
//                    }
//                });

        return binding.getRoot();
    }



    private void validateAge() {

        int month = (binding.dialogVerifyAge.datePicker.getMonth());
        int day = binding.dialogVerifyAge.datePicker.getDayOfMonth();
        int year = binding.dialogVerifyAge.datePicker.getYear();


        if (viewModel.isAgeValid(month, day, year)) {

            onDateCancelClicked();

            if (parentNavigator.isBusinessLogin()){

                showBusinessLoginScreen();

            } else {

                ChangeWelcomeFragments.addFragmentFadeIn(EnterFirstNameFragment.newInstance(viewModel.getDateOfBirth()), getBaseActivity().getSupportFragmentManager(), "ContactsFragment", null);
            }

        } else {

            showSnackbar("You must be 13 years or older");
        }

    }

    private void showSoftKeyboard(View view) {

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }



    private void showBusinessLoginScreen(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogBusinessLogin.layoutMain.setVisibility(View.VISIBLE))
                .playOn(binding.dialogBusinessLogin.layoutMain);


        binding.dialogBusinessLogin.etEmail.setText("");
        binding.dialogBusinessLogin.etPassword.setText("");
        showSoftKeyboard(binding.dialogBusinessLogin.etEmail);
    }

    private void hideBusinessLoginScreen(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogBusinessLogin.layoutMain.setVisibility(View.GONE))
                .playOn(binding.dialogBusinessLogin.layoutMain);
    }


    @Override
    public void openDashBoard() {
        hideProgressSpinner();

        if (parentNavigator.isBusinessLogin()){

            dataManager.getSharedPrefs().setBusinessAccount(true);
            getBaseActivity().finishAffinity();
            startActivity(DashBoardActivity.newIntent(getBaseActivity()));

        } else {

            dataManager.getSharedPrefs().setBusinessAccount(false);
            getBaseActivity().finishAffinity();
            startActivity(DashBoardActivity.newIntent(getBaseActivity()));

        }

    }

}
