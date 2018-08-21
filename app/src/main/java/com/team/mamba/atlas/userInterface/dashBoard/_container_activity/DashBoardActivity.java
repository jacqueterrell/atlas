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

        showToolBar();
        setNotificationObservable();
        dataManager.getSharedPrefs().setUserLoggedIn(true);

        //Load the fragment into our container
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = InfoFragment.newInstance();
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
    public void resetToFirstFragment() {

        getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        showToolBar();
    }

    @Override
    public void onContactsClicked() {

        showContactsIcon();
        hideInfoIcon();
        hideCrmIcon();
        hideNotificationsIcon();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (!(fragment instanceof ContactsFragment)) {

            ChangeFragments.replaceHorizontallyFromBackStack(new ContactsFragment(), getSupportFragmentManager(), "ContactsFragment", null);
        }
    }

    @Override
    public void onCrmClicked() {

        showCrmIcon();
        hideContactsIcon();
        hideInfoIcon();
        hideNotificationsIcon();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (!(fragment instanceof CrmFragment)) {

            ChangeFragments.replaceHorizontallyFromBackStack(new CrmFragment(), getSupportFragmentManager(), "CrmFragment", null);
        }
    }

    @Override
    public void onNotificationsClicked() {

        showNotificationsIcon();
        hideInfoIcon();
        hideCrmIcon();
        hideContactsIcon();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (!(fragment instanceof AnnouncementsFragment)) {

            ChangeFragments.replaceHorizontallyFromBackStack(AnnouncementsFragment.newInstance(), getSupportFragmentManager(), "Announcements", null);
        }
    }

    @Override
    public void onInfoClicked() {

        showInfoIcon();
        hideContactsIcon();
        hideCrmIcon();
        hideNotificationsIcon();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (!(fragment instanceof InfoFragment)) {

            ChangeFragments.replaceHorizontallyFromBackStack(InfoFragment.newInstance(), getSupportFragmentManager(), "UserProfile", null);
        }
    }

    @Override
    public void openSettingsScreen() {

        ChangeFragments.addFragmentVertically(SettingsFragment.newInstance(), getSupportFragmentManager(), "SettingsFragment", null);
        hideToolBar();
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
        if (profile.id.equals(dataManager.getSharedPrefs().getUserId())){

            ChangeFragments.addFragmentVertically(UserProfileFragment.newInstance(profile), getSupportFragmentManager(), "UserProfile", null);
            hideToolBar();

        } else {

            ChangeFragments.addFragmentVertically(ContactProfilePager.newInstance(profile), getSupportFragmentManager(), "ContactPager", null);
            hideToolBar();
        }

    }

    @Override
    public void openBusinessProfile(BusinessProfile businessProfile) {

        ChangeFragments.addFragmentVertically(BusinessProfileFragment.newInstance(businessProfile), getSupportFragmentManager(), "Business Profile", null);
        hideToolBar();
    }



    @Override
    public void showToolBar() {

        try {
            YoYo.with(Techniques.FadeIn)
                    .duration(0)
                    .onStart(animator -> binding.layoutToolBar.setVisibility(View.VISIBLE))
                    .playOn(binding.layoutToolBar);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
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
    public void hideToolBar() {

        YoYo.with(Techniques.FadeOut)
                .duration(200)
                .onEnd(animator -> binding.layoutToolBar.setVisibility(View.GONE))
                .playOn(binding.layoutToolBar);
    }

    public void showContactsIcon() {

        binding.ivContactsSelected.setVisibility(View.VISIBLE);
        binding.ivContactsNotSelected.setVisibility(View.GONE);
    }

    public void hideContactsIcon() {

        binding.ivContactsSelected.setVisibility(View.GONE);
        binding.ivContactsNotSelected.setVisibility(View.VISIBLE);
    }

    public void showCrmIcon() {

        binding.ivCrmSelected.setVisibility(View.VISIBLE);
        binding.ivCrmNotSelected.setVisibility(View.GONE);
    }

    public void hideCrmIcon() {

        binding.ivCrmSelected.setVisibility(View.GONE);
        binding.ivCrmNotSelected.setVisibility(View.VISIBLE);
    }

    public void showInfoIcon() {

        binding.ivInfoSelected.setVisibility(View.VISIBLE);
        binding.ivInfoNotSelected.setVisibility(View.GONE);
    }

    public void hideInfoIcon() {

        binding.ivInfoSelected.setVisibility(View.GONE);
        binding.ivInfoNotSelected.setVisibility(View.VISIBLE);
    }

    public void showNotificationsIcon() {

        binding.ivNotificationsSelected.setVisibility(View.VISIBLE);
        binding.ivNotificationsNotSelected.setVisibility(View.GONE);
    }

    public void hideNotificationsIcon() {

        binding.ivNotificationsSelected.setVisibility(View.GONE);
        binding.ivNotificationsNotSelected.setVisibility(View.VISIBLE);
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

                showToolBar();
            }


        }

        return false;
    }


    /**
     * Subscribes to the Observable in {@link MyFirebaseMessagingService}
     *
     */
    private void setNotificationObservable(){

        Observable<String> observable = MyFirebaseMessagingService.getObservable();
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                //show notification badges
            }
        };

        observable.subscribe(observer);
    }
}
