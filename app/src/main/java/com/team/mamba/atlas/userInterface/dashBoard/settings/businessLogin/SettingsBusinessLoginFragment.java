package com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessAccountsRecyclerViewBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SettingsBusinessLoginFragment
        extends BaseFragment<BusinessAccountsRecyclerViewBinding, SettingsBusinessLoginViewModel>
        implements SettingsBusinessLoginNavigator {


    @Inject
    SettingsBusinessLoginDataModel dataModel;

    private BusinessAccountsRecyclerViewBinding binding;
    private static List<BusinessProfile> businessProfiles = new ArrayList<>();
    private SettingsBusinessLoginAdapter adapter;
    private DashBoardActivityNavigator parentNavigator;
    private SettingsBusinessLoginViewModel viewModel;

    public static SettingsBusinessLoginFragment newInstance() {
        return new SettingsBusinessLoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentNavigator = (DashBoardActivityNavigator) context;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.business_accounts_recycler_view;
    }

    @Override
    public SettingsBusinessLoginViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.defaultSpinner.progressSpinner;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SettingsBusinessLoginViewModel.class);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
        binding = getViewDataBinding();

        adapter = new SettingsBusinessLoginAdapter(getViewModel(), businessProfiles);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);

        Handler handler = new Handler();
        showProgressSpinner();
        handler.postDelayed(() -> viewModel.checkAllBusinesses(),500);
        return binding.getRoot();
    }

    @Override
    public void onEmptyBusinessesReturned() {
        hideProgressSpinner();
        String title = "No businesses found";
        String body = "If you are a business rep, please log out of the app and back in as a business";

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        dialog.setTitle(title)
                .setMessage(body)
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
                    getBaseActivity().onBackPressed();
                })
                .setPositiveButton("Log out", (paramDialogInterface, paramInt) -> {
                    dataManager.getSharedPrefs().setUserLoggedIn(false);
                    showToastShort("Logging out");
                    resetApplication();
                });


        dialog.show();
    }

    @Override
    public void onSuccessfulBusinessProfileResponse() {
        hideProgressSpinner();
        businessProfiles.clear();
        businessProfiles.addAll(viewModel.getBusinessProfileList());
        if (viewModel.getBusinessProfileList().size() == 1) {
           // onAccountSelected(viewModel.getBusinessProfileList().get(0));
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAccountSelected(BusinessProfile profile) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        String title = "Login as " + profile.getName() + "?";
        String msg = "You are an authorized representative for " + profile.getName();

        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("No", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {
                    dataManager.getSharedPrefs().setBusinessRepId(profile.getBusinessRepId());
                    dataManager.getSharedPrefs().setUserId(profile.getId());
                    dataManager.getSharedPrefs().setUserLoggedIn(true);
                    dataManager.getSharedPrefs().setBusinessAccount(true);
                    parentNavigator.resetEntireApp();
                });

        dialog.show();

    }


    @Override
    public void handleError(String errorMsg) {
        hideProgressSpinner();
        String title = "Something went wrong...";
        showAlert(title, errorMsg);
    }

    private void resetApplication() {
        getBaseActivity().finishAffinity();
        startActivity(WelcomeActivity.newIntent(getBaseActivity()));
    }
}
