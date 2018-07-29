package com.team.mamba.atlas.userInterface.dashBoard.businessOpportunities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.BusinessOpportunitiesLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import javax.inject.Inject;

public class BusinessOpportunitiesFragment extends BaseFragment<BusinessOpportunitiesLayoutBinding,BusinessOpportunitiesViewModel>
        implements BusinessOpportunitiesNavigator {


    @Inject
    BusinessOpportunitiesViewModel viewModel;

    @Inject
    BusinessOpportunitiesDataModel dataModel;

    private BusinessOpportunitiesLayoutBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.business_opportunities_layout;
    }

    @Override
    public BusinessOpportunitiesViewModel getViewModel() {
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
