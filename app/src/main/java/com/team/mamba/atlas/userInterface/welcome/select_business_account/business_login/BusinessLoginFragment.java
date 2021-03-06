package com.team.mamba.atlas.userInterface.welcome.select_business_account.business_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessLoginLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts.AdminAccountsFragment;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler.BusinessAccountsActivity;
import com.team.mamba.atlas.utils.ChangeFragments;
import javax.inject.Inject;

public class BusinessLoginFragment extends BaseFragment<BusinessLoginLayoutBinding, BusinessLoginViewModel>
        implements BusinessLoginNavigator {


    @Inject
    BusinessLoginViewModel viewModel;

    @Inject
    BusinessLoginDataModel dataModel;


    private BusinessLoginLayoutBinding binding;
    private DashBoardActivityNavigator parentNavigator;

    public static BusinessLoginFragment newInstance() {
        return new BusinessLoginFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.business_login_layout;
    }

    @Override
    public BusinessLoginViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentNavigator = (DashBoardActivityNavigator) context;

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
        showSoftKeyboard(binding.etEmail);
        return binding.getRoot();
    }

    @Override
    public Activity getParentActivity() {
        return getBaseActivity();
    }


    @Override
    public void openDashBoard() {
        hideProgressSpinner();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());
        BusinessProfile profile = viewModel.getBusinessProfileList().get(0);
        String title = "Login as " + profile.getName();
        String body = "You are an authorized representative for " + profile.getName();

        dialog.setTitle(title)
                .setMessage(body)
                .setNegativeButton("No", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {
                    dataManager.getSharedPrefs().setUserId(profile.getId());
                    dataManager.getSharedPrefs().setUserLoggedIn(true);
                    dataManager.getSharedPrefs().setBusinessAccount(true);
                    getBaseActivity().finishAffinity();
                    startActivity(DashBoardActivity.newIntent(getBaseActivity()));
                });

        dialog.show();

    }

    @Override
    public void handleError(String errorMsg) {
        hideProgressSpinner();
        showAlert("Error", errorMsg);
    }

    @Override
    public void showMultipleBusinessLogin() {
        hideProgressSpinner();
        startActivity(BusinessAccountsActivity.newIntent(getBaseActivity(), viewModel.getBusinessProfileList()));
    }

    @Override
    public void showBusinessNotFoundAlert() {
        hideProgressSpinner();
        String title = "Login Issue";
        String body = getBaseActivity().getResources().getString(R.string.business_not_found_body);
        showAlert(title, body);
    }

    @Override
    public void showCreateUserAccountAlert() {
        hideProgressSpinner();
        String title = "User account not found";
        String body = "You must create a user account first to login as a business representative";
        showAlert(title, body);
    }

    @Override
    public void onBusinessScreenLearnMoreClicked() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_BUSINESS_URL));
        startActivity(i);
    }

    @Override
    public void onBusinessScreenLoginClicked() {

        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        hideKeyboard();

        if (email.isEmpty() && password.equals("admin")) {

            ChangeFragments.addFragmentFadeIn(AdminAccountsFragment.newInstance(),
                    getBaseActivity().getSupportFragmentManager(), "ContactsFragment", null);


        } else {

            if (viewModel.isEmailValid(email) && viewModel.isPasswordValid(password)) {

                viewModel.firebaseAuthenticateByEmail(viewModel, email, password);
                showProgressSpinner();

            } else {

                if (!viewModel.isEmailValid(email) && !viewModel.isPasswordValid(password)) {
                    showSnackbar("Please enter a valid email and password");

                } else if (!viewModel.isEmailValid(email)) {
                    showSnackbar("Please enter a valid email");

                } else {
                    showSnackbar("Please enter a valid password");
                }
            }
        }
    }


    private void showSoftKeyboard(View view) {

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }
}
