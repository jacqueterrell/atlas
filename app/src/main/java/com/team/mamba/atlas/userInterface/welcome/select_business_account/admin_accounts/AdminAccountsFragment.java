package com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.databinding.BusinessAccountsRecyclerViewBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivityNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AdminAccountsFragment extends BaseFragment<BusinessAccountsRecyclerViewBinding, AdminAccountsViewModel>
        implements AdminAccountsNavigator {


    @Inject
    AdminAccountsViewModel viewModel;

    @Inject
    AdminAccountsDataModel dataModel;


    private BusinessAccountsRecyclerViewBinding binding;
    private List<UserProfile> adminProfiles = new ArrayList<>();
    private AdminAccountsAdapter adminAccountsAdapter;
    private WelcomeActivityNavigator parentNavigator;


    public static AdminAccountsFragment newInstance(){

        return new AdminAccountsFragment();
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
    public AdminAccountsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
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

        adminAccountsAdapter = new AdminAccountsAdapter(adminProfiles, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adminAccountsAdapter);

        showProgressSpinner();
        viewModel.getAllAdminProfiles(getViewModel());

        return binding.getRoot();
    }

    @Override
    public void handleError(String errorMsg) {

        showAlert("Error",errorMsg);
        hideProgressSpinner();
    }

    @Override
    public void onAdminProfileSelected(UserProfile userProfile) {

        dataManager.getSharedPrefs().setUserId(userProfile.getId());
        parentNavigator.setBusinessLogin(false);
        openDashBoard();

    }

    @Override
    public void onAdminProfilesReturned() {

        adminProfiles.clear();
        adminProfiles.addAll(viewModel.getAdminProfileList());
        adminAccountsAdapter.notifyDataSetChanged();
        hideProgressSpinner();
    }

    @Override
    public void openDashBoard() {

        hideProgressSpinner();

        if (parentNavigator.isBusinessLogin()) {

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
