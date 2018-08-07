package com.team.mamba.atlas.userInterface.dashBoard.user_profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.UserProfileLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import javax.inject.Inject;

public class UserProfileFragment extends BaseFragment<UserProfileLayoutBinding,UserProfileViewModel>
        implements UserProfileNavigator {


    @Inject
    UserProfileViewModel viewModel;

    private UserProfileLayoutBinding binding;

    public static UserProfileFragment newInstance(){

        return new UserProfileFragment();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_profile_layout;
    }

    @Override
    public UserProfileViewModel getViewModel() {
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         binding = getViewDataBinding();


         return binding.getRoot();
    }

    @Override
    public void onUserProfileClicked() {

    }
}
