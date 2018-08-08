package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.databinding.FragmentContainerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_business.AddBusinessFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user.AddUserFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.find_users.FindUsersFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.suggested_contacts.SuggestedContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.business_profile.BusinessProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;

import com.team.mamba.atlas.userInterface.dashBoard.user_profile.UserProfileFragment;
import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerActivity;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeFragment;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.ChangeFragments;

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

        String version = "Version " + BuildConfig.VERSION_NAME;
        binding.dialogSettings.tvVersionName.setText(version);

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

    @Override
    public void openSettingsScreen() {

        showSettings();
    }

    @Override
    public void openAddContactDialog() {

        showAddContactDialog();
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {

        viewModel.setUserProfile(userProfile);
    }

    @Override
    public UserProfile getUserProfile() {
        return viewModel.getUserProfile();
    }

    @Override
    public void openUserProfile() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(UserProfileFragment.newInstance(),getSupportFragmentManager(),"UserProfile",null);
        binding.layoutToolBar.setVisibility(View.GONE);
    }

    @Override
    public void openBusinessProfile() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(BusinessProfileFragment.newInstance(),getSupportFragmentManager(),"Business Profile",null);
        binding.layoutToolBar.setVisibility(View.GONE);
    }

    @Override
    public void onSiteLinkClicked() {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.ATLAS_SITE_URL));
        startActivity(i);
    }

    @Override
    public void onAddUserClicked() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(AddUserFragment.newInstance(),getSupportFragmentManager(),"AddUser",null);
        binding.layoutToolBar.setVisibility(View.GONE);
    }

    @Override
    public void onAddBusinessClicked() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(AddBusinessFragment.newInstance(),getSupportFragmentManager(),"AddBusiness",null);
        binding.layoutToolBar.setVisibility(View.GONE);
    }

    @Override
    public void onInviteToAtlasClicked() {

        final String appPackageName = BuildConfig.APPLICATION_ID; // package name of the app

        String msg = "Join me on Atlas Networking! " + AppConstants.BASE_PLAY_STORE_LINK +  appPackageName;

        hideAddContactDialog();
//        Intent It = new Intent(Intent.ACTION_SEND);
//        It.setType("text/plain");
//        It.putExtra(android.content.Intent.EXTRA_TEXT,msg);
//        startActivity(It);


        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(msg)
                .startChooser();
    }

    @Override
    public void onFindUsersClicked() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(FindUsersFragment.newInstance(),getSupportFragmentManager(),"FindUsers",null);
        binding.layoutToolBar.setVisibility(View.GONE);

    }

    @Override
    public void onAddSuggestedContactsClicked() {

        hideAddContactDialog();
        ChangeFragments.addFragmentVertically(SuggestedContactsFragment.newInstance(),getSupportFragmentManager(),"SuggestedContacts",null);
        binding.layoutToolBar.setVisibility(View.GONE);

    }

    @Override
    public void onCancelAddDialogClicked() {

        hideAddContactDialog();
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
    public void onPrivacyPolicyClicked() {


    }

    @Override
    public void onTermsOfServiceClicked() {


    }

    @Override
    public void onNetworkManagementClicked() {

    }

    @Override
    public void onLogOutClicked() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Log Out")
                .setMessage("Do you want to log out of this account?")
                .setNegativeButton("no", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("yes", (paramDialogInterface, paramInt) -> {

                    dataManager.getSharedPrefs().setUserLoggedIn(false);
                    showToastShort("Logging out");
                    finishAffinity();
                    startActivity(ViewPagerActivity.newIntent(DashBoardActivity.this));
                });

        dialog.show();

    }

    @Override
    public void onDeleteMyAccountClicked() {

    }

    @Override
    public void showToolBar() {

        try {
            binding.layoutToolBar.setVisibility(View.VISIBLE);
        }catch (Exception e){

        }
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

    private void showSettings(){

        YoYo.with(Techniques.SlideInUp)
                .duration(500)
                .onStart(animator -> binding.dialogSettings.layoutDashboardSettings.setVisibility(View.VISIBLE))
                .playOn(binding.dialogSettings.layoutDashboardSettings);
    }

    private void hideSettings(){

        YoYo.with(Techniques.SlideOutDown)
                .duration(500)
                .onEnd(animator -> binding.dialogSettings.layoutDashboardSettings.setVisibility(View.GONE))
                .playOn(binding.dialogSettings.layoutDashboardSettings);
    }

    private void showAddContactDialog(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogAddContact.setVisibility(View.VISIBLE))
                .playOn(binding.dialogAddContact);
    }

    private void hideAddContactDialog(){

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogAddContact.setVisibility(View.GONE))
                .playOn(binding.dialogAddContact);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 4){


            if (binding.dialogSettings.layoutDashboardSettings.getVisibility() == View.VISIBLE){

                hideSettings();

            } else if (binding.dialogAddContact.getVisibility() == View.VISIBLE){

                hideAddContactDialog();

            } else {

                onBackPressed();

            }

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (fragment instanceof InfoFragment){

                binding.layoutToolBar.setVisibility(View.VISIBLE);
            }

        }

        return false;
    }
}
