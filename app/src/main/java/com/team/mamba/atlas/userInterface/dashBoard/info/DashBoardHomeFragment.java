package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.databinding.DashboardHomeLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import javax.inject.Inject;
import com.team.mamba.atlas.R;

public class DashBoardHomeFragment extends BaseFragment<DashboardHomeLayoutBinding,DashBoardHomeViewModel>
        implements DashBoardHomeNavigator {

    @Inject
    DashBoardHomeViewModel viewModel;

    @Inject
    InfoDataModel dataModel;

    private DashboardHomeLayoutBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dashboard_home_layout;
    }

    @Override
    public DashBoardHomeViewModel getViewModel() {
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
