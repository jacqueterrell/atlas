package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.add_business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.AddBusinessLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class AddBusinessFragment extends BaseFragment<AddBusinessLayoutBinding, AddBusinessViewModel>
implements AddBusinessNavigator {


    @Inject
    AddBusinessViewModel viewModel;


    private AddBusinessLayoutBinding binding;

    public static AddBusinessFragment newInstance(){

        return new AddBusinessFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.add_business_layout;
    }

    @Override
    public AddBusinessViewModel getViewModel() {
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
}
