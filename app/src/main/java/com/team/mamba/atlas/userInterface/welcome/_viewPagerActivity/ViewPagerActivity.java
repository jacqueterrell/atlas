package com.team.mamba.atlas.userInterface.welcome._viewPagerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;

import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeViewPagerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.welcome.howYouMet.RememberHowYouMetFragment;
import com.team.mamba.atlas.userInterface.welcome.latestNews.LatestNewsFragment;
import com.team.mamba.atlas.userInterface.welcome.mobileCrm.MobileCrmFragment;
import com.team.mamba.atlas.userInterface.welcome.upToDate.UpToDateFragment;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeFragment;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeNavigator;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ViewPagerActivity extends BaseActivity<WelcomeViewPagerBinding, ViewPagerViewModel>
        implements ViewPagerNavigator, HasSupportFragmentInjector {

    @Inject
    ViewPagerViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;


    private static final int SCROLL_ACTION_STOPPED = 0;
    private WelcomeViewPagerBinding binding;
    private WelcomePager welcomePager;
    private int currentPage;

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

    private class WelcomePager extends FragmentStatePagerAdapter {


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

            } else if (position == 3) {

                return MobileCrmFragment.newInstance();
            } else if (position == 4) {

                return LatestNewsFragment.newInstance();
            } else if (position == 5) {

                return WelcomeFragment.newInstance();

            } else {

                return WelcomeFragment.newInstance();

            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            ((ViewPager) container).addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

//                        if (currentPage ==0){
//
//                            ((ViewPager)container).setCurrentItem(5,false);
//                        }

                    if (currentPage == 5)
                        ((ViewPager) container).setCurrentItem(0, false);

                }
            });

            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 4) {

            if (binding.viewPagerWelcome.getCurrentItem() == 0) {

                Fragment fragment = (Fragment) binding.viewPagerWelcome
                        .getAdapter()
                        .instantiateItem(binding.viewPagerWelcome, 0);


                if (fragment instanceof WelcomeFragment) {

                    WelcomeNavigator navigator = (WelcomeNavigator) fragment;
                    navigator.onBackPressed();

                }

            } else {

                onBackPressed();
            }

        }

        return false;
    }


}


