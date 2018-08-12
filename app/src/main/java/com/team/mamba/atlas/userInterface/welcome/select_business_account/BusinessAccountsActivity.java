package com.team.mamba.atlas.userInterface.welcome.select_business_account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessAccountsRecyclerViewBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BusinessAccountsActivity
        extends BaseActivity<BusinessAccountsRecyclerViewBinding, BusinessAccountsViewModel>
        implements BusinessAccountsNavigator {


    @Inject
    BusinessAccountsViewModel viewModel;

    private BusinessAccountsRecyclerViewBinding binding;
    private static List<BusinessProfile> businessProfiles = new ArrayList<>();



    public static Intent newIntent(Context context, List<BusinessProfile>profiles) {

        businessProfiles = profiles;
        return new Intent(context, BusinessAccountsActivity.class);
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
    public BusinessAccountsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }

    @Override
    public void onAccountSelected(BusinessProfile profile) {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        String title = "Login as " + profile.getName() + "?";
        String msg = "You are an authorized representative for " + profile.getName();

        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("No", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {

                    finishAffinity();

                    dataManager.getSharedPrefs().setUserId(profile.getId());
                    dataManager.getSharedPrefs().setUserLoggedIn(true);
                    dataManager.getSharedPrefs().setBusinessAccount(true);
                    startActivity(DashBoardActivity.newIntent(BusinessAccountsActivity.this));
                });

        dialog.show();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();


        BusinessAccountsAdapter accountsAdapter = new BusinessAccountsAdapter(getViewModel(), businessProfiles);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(accountsAdapter);

    }
}
