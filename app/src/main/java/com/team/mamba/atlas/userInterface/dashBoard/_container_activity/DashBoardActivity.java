package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;
import com.team.mamba.atlas.databinding.FragmentContainerBinding;
import com.team.mamba.atlas.service.MyFirebaseMessagingService;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.dashBoard.announcements.AnnouncementsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.AddContactDialogFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.edit_add_note.EditAddNotePageOneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.edit_add_note.EditPageOneNavigator;
import com.team.mamba.atlas.userInterface.dashBoard.crm.main.CrmFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.main.CrmNavigator;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.user_business.BusinessProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.ContactProfilePager;
import com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.UserProfileFragment;
import com.team.mamba.atlas.userInterface.dashBoard.settings.SettingsFragment;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.ChangeFragments;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class DashBoardActivity extends BaseActivity<FragmentContainerBinding, DashBoardActivityViewModel>
        implements DashBoardActivityNavigator, HasSupportFragmentInjector {


    @Inject
    DashBoardActivityViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;


    private FragmentContainerBinding binding;
    private static boolean contactRecentlyDeleted = false;
    private static boolean contactRecentlyAdded = false;
    public static int newRequestCount = 0;
    public static int newAnnouncementCount = 0;


    public static Intent newIntent(Context context) {

        return new Intent(context, DashBoardActivity.class);
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

        if (MyFirebaseMessagingService.isBusinessAnnouncement){

            if (fragment == null) {

                fragment = AnnouncementsFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();

            } else {

                fragment = AnnouncementsFragment.newInstance();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            MyFirebaseMessagingService.isBusinessAnnouncement = false;

        } else {

            if (fragment == null) {
                fragment = InfoFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void resetToFirstFragment() {

        getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void refreshInfoFragment() {

        finishAffinity();
        startActivity(DashBoardActivity.newIntent(DashBoardActivity.this));
    }

    @Override
    public void openSettingsScreen() {

        ChangeFragments.addFragmentVertically(SettingsFragment.newInstance(), getSupportFragmentManager(), "SettingsFragment", null);
    }

    @Override
    public void openAddContactDialog() {

//        showAddContactDialog();
        AddContactDialogFragment dialog = new AddContactDialogFragment();
        dialog.show(getSupportFragmentManager(), "ContactDialog");
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
    public void openUserProfile(UserProfile profile) {

        //check if the profile is a contacts or the signed in use
        if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

            ChangeFragments.addFragmentVertically(UserProfileFragment.newInstance(), getSupportFragmentManager(), "UserProfile", null);

        } else {

            ChangeFragments.addFragmentVertically(ContactProfilePager.newInstance(profile), getSupportFragmentManager(), "ContactPager", null);
        }

    }

    @Override
    public void openBusinessProfile(BusinessProfile businessProfile) {

        ChangeFragments.addFragmentVertically(BusinessProfileFragment.newInstance(businessProfile), getSupportFragmentManager(), "Business Profile", null);
    }


    @Override
    public boolean wasContactRecentlyDeleted() {
        return contactRecentlyDeleted;
    }

    @Override
    public boolean wasContactRecentlyAdded() {
        return contactRecentlyAdded;
    }

    @Override
    public void setContactRecentlyAdded(boolean wasAdded) {
        contactRecentlyAdded = wasAdded;
    }

    @Override
    public void setContactRecentlyDeleted(boolean wasDeleted) {

        contactRecentlyDeleted = wasDeleted;
    }


    @Override
    public void setCrmFilter(CrmFilter crmFilter) {

        viewModel.setCrmFilter(crmFilter);
    }

    @Override
    public CrmFilter getCrmFilter() {
        return viewModel.getCrmFilter();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 4) {

            Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (previousFragment instanceof CrmFragment) {

                CrmNavigator navigator = (CrmNavigator) previousFragment;

                if (navigator.isInfoDialogShown()) {

                    navigator.hideCrmInfoDialog();

                } else if (navigator.isExportScreenShown()) {

                    navigator.hideExportScreen();

                } else {
                    onBackPressed();
                }

            } else if (previousFragment instanceof EditAddNotePageOneFragment) {

                EditPageOneNavigator navigator = (EditPageOneNavigator) previousFragment;

                if (navigator.isContactsScreenShown()) {

                    navigator.closeContactsScreen();

                } else {

                    onBackPressed();
                }

            } else {

                onBackPressed();
            }

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof InfoFragment
                    || currentFragment instanceof CrmFragment
                    || currentFragment instanceof AnnouncementsFragment
                    || currentFragment instanceof ContactsFragment) {

            }


        }

        return false;
    }

}
