package com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.AddUserLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.utils.ChangeFragments;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.describe_connections.DescribeConnectionsFragment;

import javax.inject.Inject;

public class AddUserFragment extends BaseFragment<AddUserLayoutBinding, AddUserViewModel>
implements AddUserNavigator {


    @Inject
    AddUserViewModel viewModel;

    private AddUserLayoutBinding binding;

    public static AddUserFragment newInstance(){

        return new AddUserFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.add_user_layout;
    }

    @Override
    public AddUserViewModel getViewModel() {
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
    public void onNextButtonClicked() {

        String lastName = binding.etLastName.getText().toString();
        String userCode = binding.etUserCode.getText().toString();

        if (!viewModel.isLastNameValid(lastName) || !viewModel.isUserCodeValid(userCode)){

            if (!viewModel.isLastNameValid(lastName) && !viewModel.isUserCodeValid(userCode)){

                showSnackbar("Please enter a valid user code and last name");

            } else if (!viewModel.isLastNameValid(lastName)){

                showSnackbar("Please enter a valid last name");

            } else {

                showSnackbar("Please enter a valid user code");

            }

        } else {

            Bundle args = new Bundle();
            args.putString("lastName",lastName);
            args.putString("userCode",userCode);

            ChangeFragments.addFragmentFadeIn(DescribeConnectionsFragment.newInstance(lastName,userCode),getBaseActivity()
                    .getSupportFragmentManager(),"AddUserLayout",args);

            //open next screen
        }
    }
}
