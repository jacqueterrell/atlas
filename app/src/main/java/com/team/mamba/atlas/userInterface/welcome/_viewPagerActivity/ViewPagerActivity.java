package com.team.mamba.atlas.userInterface.welcome._viewPagerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeViewPagerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.welcome.howYouMet.RememberHowYouMetFragment;
import com.team.mamba.atlas.userInterface.welcome.mobileCrm.MobileCrmFragment;
import com.team.mamba.atlas.userInterface.welcome.upTodDate.UpToDateFragment;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ViewPagerActivity extends BaseActivity<WelcomeViewPagerBinding,ViewPagerViewModel>
        implements ViewPagerNavigator, HasSupportFragmentInjector {

    @Inject
    ViewPagerViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;


    private WelcomeViewPagerBinding binding;
    private WelcomePager welcomePager;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_view_pager;
    }

    @Override
    public ViewPagerViewModel getViewModel() {
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

        welcomePager = new WelcomePager(getSupportFragmentManager());
        binding.viewPagerWelcome.setAdapter(welcomePager);

    }

    private class WelcomePager extends FragmentStatePagerAdapter{


        public WelcomePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {

                return WelcomeFragment.newInstance();

            } else if (position == 1) {

                return UpToDateFragment.newInstance();

            } else if (position == 2) {

                return RememberHowYouMetFragment.newInstance();

            } else  {

                return MobileCrmFragment.newInstance();

            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
