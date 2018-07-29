package com.team.mamba.atlas.userInterface.welcome._viewPagerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeViewPagerBinding;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import javax.inject.Inject;

public class ViewPagerActivity extends BaseActivity<WelcomeViewPagerBinding,ViewPagerViewModel> implements ViewPagerNavigator {

    @Inject
    ViewPagerViewModel viewModel;

    private WelcomeViewPagerBinding binding;

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
    }
}
