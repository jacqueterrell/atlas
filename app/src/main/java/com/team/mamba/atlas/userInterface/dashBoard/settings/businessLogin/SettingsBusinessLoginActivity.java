package com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessAccountsRecyclerViewBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler.BusinessAccountsAdapter;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler.BusinessAccountsNavigator;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler.BusinessAccountsViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SettingsBusinessLoginActivity
        extends BaseActivity<BusinessAccountsRecyclerViewBinding, SettingsBusinessLoginViewModel>
        implements SettingsBusinessLoginNavigator {


    @Inject
    SettingsBusinessLoginViewModel viewModel;

    @Inject
    SettingsBusinessLoginDataModel dataModel;

    private BusinessAccountsRecyclerViewBinding binding;
    private static List<BusinessProfile> businessProfiles = new ArrayList<>();
    private SettingsBusinessLoginAdapter adapter;


    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsBusinessLoginActivity.class);
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
                    startActivity(DashBoardActivity.newIntent(SettingsBusinessLoginActivity.this));
                });

        dialog.show();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
        binding = getViewDataBinding();

        adapter = new SettingsBusinessLoginAdapter(getViewModel(),businessProfiles);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void onEmptyBusinessesReturned() {
        String title = "No business found";
        String body = "Please log out of the app and back in....";
        showAlertDialog(title,body);
    }

    @Override
    public void onSuccessfulBusinessProfileResponse() {
        businessProfiles.clear();
        businessProfiles.addAll(viewModel.getBusinessProfileList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleError(String errorMsg) {
        String title = "Something went wrong...";
        showAlertDialog(title,errorMsg);
    }
}
