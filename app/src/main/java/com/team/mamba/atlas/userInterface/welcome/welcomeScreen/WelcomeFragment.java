package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeScreenLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import javax.inject.Inject;

public class WelcomeFragment extends BaseFragment<WelcomeScreenLayoutBinding,WelcomeViewModel> implements WelcomeNavigator{

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeDataModel dataModel;

    private WelcomeScreenLayoutBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_screen_layout;
    }

    @Override
    public WelcomeViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        binding = getViewDataBinding();

        return binding.getRoot();
    }
}
