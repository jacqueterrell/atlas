package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.EditEducationLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class EditEducationFragment extends BaseFragment<EditEducationLayoutBinding,EditEducationViewModel> implements EditEducationNavigator {


    @Inject
    EditEducationViewModel viewModel;

    @Inject
    public EditEducationDataModel dataModel;

    private EditEducationLayoutBinding binding;
    private UserProfile profile;



    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_education_layout;
    }

    @Override
    public EditEducationViewModel getViewModel() {
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
