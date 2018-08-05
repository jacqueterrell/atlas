package com.team.mamba.atlas.userInterface.welcome.select_business_account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.BusinessAccountsRecyclerViewBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class BusinessAccountsActivity
        extends BaseActivity<BusinessAccountsRecyclerViewBinding, BusinessAccountsViewModel>
        implements BusinessAccountsNavigator {


    @Inject
    BusinessAccountsViewModel viewModel;

    private BusinessAccountsRecyclerViewBinding binding;
    private List<String> namesList = new ArrayList<>();
    private List<String> businessIdList = new ArrayList<>();
    private static Map<String, String> accountsMap;


    public static Intent newIntent(Context context, Map<String, String> namesMap) {

        accountsMap = namesMap;
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
    public void getSelectedPosition(int position) {

        String userId = businessIdList.get(position);
        dataManager.getSharedPrefs().setUserId(userId);
        dataManager.getSharedPrefs().setUserLoggedIn(true);
        dataManager.getSharedPrefs().setBusinessAccount(true);

        finishAffinity();

        startActivity(DashBoardActivity.newIntent(BusinessAccountsActivity.this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();

        for (Map.Entry<String, String> entry : accountsMap.entrySet()) {

            String businessId = entry.getKey();
            String businessName = entry.getValue();

            businessIdList.add(businessId);
            namesList.add(businessName);

        }

        BusinessAccountsAdapter accountsAdapter = new BusinessAccountsAdapter(getViewModel(), namesList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(accountsAdapter);

    }
}
