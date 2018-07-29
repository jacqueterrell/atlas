package com.team.mamba.atlas.userInterface.dashBoard._container_activity;

import android.view.View;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.userInterface.base.BaseActivity;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import javax.inject.Inject;

public class DashBoardActivity extends BaseActivity implements DashBoardActivityNavigator {


    @Inject
    DashBoardActivityViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public DashBoardActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }
}
