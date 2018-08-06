package com.team.mamba.atlas.userInterface.dashBoard._container_activity.find_users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.FindUsersLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class FindUsersFragment extends BaseFragment<FindUsersLayoutBinding, FindUsersViewModel>
implements FindUsersNavigator{

    @Inject
    FindUsersViewModel viewModel;


    private FindUsersLayoutBinding binding;

    public static FindUsersFragment newInstance(){

        return new FindUsersFragment();
    }

    @Override
    public int getBindingVariable() {

        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.find_users_layout;
    }

    @Override
    public FindUsersViewModel getViewModel() {
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
    public void onSearchButtonClicked() {

    }
}
