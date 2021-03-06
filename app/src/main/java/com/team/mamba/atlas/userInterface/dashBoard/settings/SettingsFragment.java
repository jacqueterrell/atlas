package com.team.mamba.atlas.userInterface.dashBoard.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.SettingsLayoutBinding;
import com.team.mamba.atlas.service.AddContactsCrmService;
import com.team.mamba.atlas.service.IncompleteProfileInfoService;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin.SettingsBusinessLoginFragment;
import com.team.mamba.atlas.userInterface.dashBoard.settings.network_management.NetworkManagementFragment;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import com.team.mamba.atlas.userInterface.welcome._viewPager.ViewPagerFragment;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_login.BusinessLoginFragment;
import com.team.mamba.atlas.utils.ChangeFragments;

import java.io.InputStream;
import javax.inject.Inject;

public class SettingsFragment extends BaseFragment<SettingsLayoutBinding, SettingsViewModel>
        implements SettingsNavigator {


    @Inject
    SettingsViewModel viewModel;

    @Inject
    SettingsDataModel dataModel;

    private SettingsLayoutBinding binding;
    private DashBoardActivityNavigator parentNavigator;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentNavigator = (DashBoardActivityNavigator) context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.settings_layout;
    }

    @Override
    public SettingsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.defaultSpinner.progressSpinner;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        setLoginButton();
        String version = "Version " + BuildConfig.VERSION_NAME;
        binding.tvVersionName.setText(version);

        return binding.getRoot();
    }

    /**
     * Shows the correct button based on if our user is logged
     * in as a business or not.
     */
    private void setLoginButton() {
        if (dataManager.getSharedPrefs().isBusinessAccount()) {
            binding.tvBuisinessLogin.setVisibility(View.GONE);
            binding.tvUserLogin.setVisibility(View.VISIBLE);

        } else {
            binding.tvBuisinessLogin.setVisibility(View.VISIBLE);
            binding.tvUserLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSiteLinkClicked() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_SITE_URL));
        startActivity(i);
    }


    @Override
    public void onCorporateDirectoryClicked() {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_BUSINESS_URL));
        startActivity(i);
    }

    @Override
    public void onOrganizationalOutreachClicked() {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_BUSINESS_URL));
        startActivity(i);
    }

    @Override
    public void onAlumniNetworkingClicked() {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_BUSINESS_URL));
        startActivity(i);
    }

    @Override
    public void onUserLoginClicked() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());
        String title = "Switch Accounts";
        String msg = "Do you want to login to your user account?";

        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("No", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {
                    String repId = dataManager.getSharedPrefs().getBusinessRepId();
                    dataManager.getSharedPrefs().setUserId(repId);
                    dataManager.getSharedPrefs().setUserLoggedIn(true);
                    dataManager.getSharedPrefs().setBusinessAccount(false);
                    parentNavigator.resetEntireApp();
                });

        dialog.show();


    }

    @Override
    public void onBusinessLoginClick() {
        showBusinessLoginAlert(getBaseActivity());
    }

    @Override
    public void onPrivacyPolicyClicked() {

        showTermsOfService();

        try {

            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.privacy);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            binding.tvTerms.setText(new String(b));

        } catch (Exception e) {
            binding.tvTerms.setText("Error: can't show terms.");
        }
    }

    @Override
    public void onTermsOfServiceClicked() {

        showTermsOfService();

        try {

            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.terms);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            binding.tvTerms.setText(new String(b));

        } catch (Exception e) {
            binding.tvTerms.setText("Error: can't show terms.");
        }
    }

    @Override
    public void onNetworkManagementClicked() {
        ChangeFragments.addFragmentVertically(NetworkManagementFragment.newInstance(),
                getBaseActivity().getSupportFragmentManager(), "NetworkManagement", null);

    }


    @Override
    public void onLogOutClicked() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        dialog.setTitle("Log Out")
                .setMessage("Do you want to log out of this account?")
                .setNegativeButton("no", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("yes", (paramDialogInterface, paramInt) -> {

                    dataManager.getSharedPrefs().setUserLoggedIn(false);
                    showToastShort("Logging out");
                    resetApplication();
                    stopIncompleteProfileDataService();
                    stopAddCrmAndContactsService();
                })
                .show();

    }

    private void stopIncompleteProfileDataService() {
        Intent intentService = new Intent(getContext(), IncompleteProfileInfoService.class);
        getBaseActivity().stopService(intentService);
    }

    private void stopAddCrmAndContactsService() {
        Intent intentService = new Intent(getContext(), AddContactsCrmService.class);
        getBaseActivity().stopService(intentService);
    }


    @Override
    public void onDeleteMyAccountClicked() {

        String title = "Delete Your Atlas Account?";
        String msg
                = "If you would like to delete your Atlas account, please confirm, and login to complete the process.";

        final AlertDialog.Builder dialog = new Builder(getBaseActivity());

        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Confirm", (paramDialogInterface, paramInt) -> {

                    viewModel.deleteUser();
                    showToastShort("Account deleted");
                    dataManager.getSharedPrefs().setUserLoggedIn(false);
                    resetApplication();
                })
                .show();
    }


    private void showTermsOfService() {

        YoYo.with(Techniques.SlideInUp)
                .duration(500)
                .onStart(animator -> binding.layoutTermsOfService.setVisibility(View.VISIBLE))
                .playOn(binding.layoutTermsOfService);

    }

    private void showBusinessLoginAlert(Activity activity) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        final String viewBusiness = getResources().getString(R.string.business_login_view_business);
        final String loginToBusiness = getResources().getString(R.string.business_login_new_business);
        FragmentManager manager = getBaseActivity().getSupportFragmentManager();

        dialog.setTitle("Business Login")
                .setNegativeButton(loginToBusiness, (paramDialogInterface, paramInt) -> {
                    ChangeFragments
                            .addFragmentVertically(BusinessLoginFragment.newInstance(), manager, "BusinessLogin",
                                    null);
                })
                .setPositiveButton(viewBusiness, (paramDialogInterface, paramInt) -> {
                    ChangeFragments.addFragmentVertically(SettingsBusinessLoginFragment.newInstance(), manager, "SettingsBusinessLogin", null);
                })
                .setNeutralButton("Cancel", (paramDialogInterface, paramInt) -> {
                });
        dialog.show();
    }


    /**
     * Removes all Activities from the back stack and opens up
     * {@link ViewPagerFragment}
     */
    private void resetApplication() {
        getBaseActivity().finishAffinity();
        startActivity(WelcomeActivity.newIntent(getBaseActivity()));
    }
}
