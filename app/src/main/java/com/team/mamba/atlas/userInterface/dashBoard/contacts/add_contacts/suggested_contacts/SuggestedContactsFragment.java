package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.SuggestedContactsLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class SuggestedContactsFragment extends BaseFragment<SuggestedContactsLayoutBinding,SuggestedContactsViewModel>
implements SuggestedContactsNavigator {


    @Inject
    SuggestedContactsViewModel viewModel;


    private SuggestedContactsLayoutBinding binding;


    public static SuggestedContactsFragment newInstance(){

        return new SuggestedContactsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.suggested_contacts_layout;
    }

    @Override
    public SuggestedContactsViewModel getViewModel() {
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

        showSnackbar("searched");
    }
}
