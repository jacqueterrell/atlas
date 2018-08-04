package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import android.app.FragmentContainer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.FragmentContainerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DashBoardActivity extends BaseActivity<FragmentContainerBinding,DashBoardActivityViewModel>
        implements DashBoardActivityNavigator,HasSupportFragmentInjector {


    @Inject
    DashBoardActivityViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;



    private FragmentContainerBinding binding;

    public static Intent newIntent(Context context){

        return new Intent(context,DashBoardActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public DashBoardActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();

        dataManager.getSharedPrefs().setUserLoggedIn(true);

        //Load the fragment into our container
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment =  InfoFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onContactsClicked() {

        showContactsIcon();
        hideInfoIcon();
        hideCrmIcon();
        hideNotificationsIcon();
    }

    @Override
    public void onCrmClicked() {

        showCrmIcon();
        hideContactsIcon();
        hideInfoIcon();
        hideNotificationsIcon();
    }

    @Override
    public void onNotificationsClicked() {

        showNotificationsIcon();
        hideInfoIcon();
        hideCrmIcon();
        hideContactsIcon();
    }

    @Override
    public void onInfoClicked() {

        showInfoIcon();
        hideContactsIcon();
        hideCrmIcon();
        hideNotificationsIcon();
    }

    private void showContactsIcon(){

        binding.ivContactsSelected.setVisibility(View.VISIBLE);
        binding.ivContactsNotSelected.setVisibility(View.GONE);
    }

    private void hideContactsIcon(){

        binding.ivContactsSelected.setVisibility(View.GONE);
        binding.ivContactsNotSelected.setVisibility(View.VISIBLE);
    }

    private void showCrmIcon(){

        binding.ivCrmSelected.setVisibility(View.VISIBLE);
        binding.ivCrmNotSelected.setVisibility(View.GONE);
    }

    private void hideCrmIcon(){

        binding.ivCrmSelected.setVisibility(View.GONE);
        binding.ivCrmNotSelected.setVisibility(View.VISIBLE);
    }

    private void showInfoIcon(){

        binding.ivInfoSelected.setVisibility(View.VISIBLE);
        binding.ivInfoNotSelected.setVisibility(View.GONE);
    }

    private void hideInfoIcon(){

        binding.ivInfoSelected.setVisibility(View.GONE);
        binding.ivInfoNotSelected.setVisibility(View.VISIBLE);
    }

    private void showNotificationsIcon(){

        binding.ivNotificationsSelected.setVisibility(View.VISIBLE);
        binding.ivNotificationsNotSelected.setVisibility(View.GONE);
    }

    private void hideNotificationsIcon(){

        binding.ivNotificationsSelected.setVisibility(View.GONE);
        binding.ivNotificationsNotSelected.setVisibility(View.VISIBLE);
    }
}
