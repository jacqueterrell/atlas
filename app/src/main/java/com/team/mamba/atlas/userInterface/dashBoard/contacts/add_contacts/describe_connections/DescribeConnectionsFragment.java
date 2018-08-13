package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.DescribeConnectionBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class DescribeConnectionsFragment extends BaseFragment<DescribeConnectionBinding,DescribeConnectionsViewModel>
        implements DescribeConnectionsNavigator {

    @Inject
    DescribeConnectionsViewModel viewModel;

    private DescribeConnectionBinding binding;
    private static String lastName;
    private static String userCode;

    public static DescribeConnectionsFragment newInstance(String name,String code){

        lastName = name;
        userCode = code;
        return new DescribeConnectionsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.describe_connection;
    }

    @Override
    public DescribeConnectionsViewModel getViewModel() {
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
    public void onFinishButtonClicked() {

        //call datamodel
    }

    @Override
    public void onInfoClicked() {

        showConnectionsInfo();
    }


    @Override
    public void hideConnectionsInfo() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogConnectionsInfo.setVisibility(View.GONE))
                .playOn(binding.dialogConnectionsInfo);
    }

    public void showConnectionsInfo(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogConnectionsInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogConnectionsInfo);
    }
}
