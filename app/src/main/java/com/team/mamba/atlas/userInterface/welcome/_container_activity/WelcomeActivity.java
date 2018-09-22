package com.team.mamba.atlas.userInterface.welcome._container_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeFragmentContainerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.welcome._viewPager.ViewPagerFragment;
import com.team.mamba.atlas.userInterface.welcome._viewPager.ViewPagerNavigator;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity<WelcomeFragmentContainerBinding,WelcomeActivityViewModel> implements
        WelcomeActivityNavigator, HasSupportFragmentInjector {


    @Inject
    WelcomeActivityViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;



    private WelcomeFragmentContainerBinding binding;


    public static Intent newIntent(Context context){

        return new Intent(context,WelcomeActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_fragment_container;
    }

    @Override
    public WelcomeActivityViewModel getViewModel() {
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

        //Load the fragment into our container
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.welcome_fragment_container);

        if (fragment == null) {
            fragment = new ViewPagerFragment();
            fm.beginTransaction()
                    .add(R.id.welcome_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean isBusinessLogin() {
        return viewModel.isBusinessLogin();
    }

    @Override
    public void setBusinessLogin(boolean businessLogin) {
        viewModel.setBusinessLogin(businessLogin);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 4){

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.welcome_fragment_container);

            if (fragment instanceof ViewPagerFragment){

                ViewPagerNavigator navigator = (ViewPagerNavigator) fragment;
                navigator.onKeyDown();

            } else {

                onBackPressed();
            }

            return false;
        }

        return super.onKeyDown(keyCode,event);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
