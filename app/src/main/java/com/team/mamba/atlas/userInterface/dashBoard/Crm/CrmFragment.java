package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.databinding.BusinessOpportunitiesLayoutBinding;
import com.team.mamba.atlas.databinding.CrmLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CrmFragment extends BaseFragment<CrmLayoutBinding,CrmViewModel>
        implements CrmNavigator {


    @Inject
    CrmViewModel viewModel;

    @Inject
    CrmDataModel dataModel;

    private CrmLayoutBinding binding;
    private List<CrmNotes> crmNotesList = new ArrayList<>();
    private CrmAdapter crmAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.crm_layout;
    }

    @Override
    public CrmViewModel getViewModel() {
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

         crmAdapter = new CrmAdapter(getViewModel(),crmNotesList);
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
         binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
         binding.recyclerView.setAdapter(crmAdapter);


         if (viewModel.getCrmNotesList().isEmpty()){

             viewModel.requestAllOpportunites(getViewModel());

         } else {

             onCrmDataReturned();
         }

         return binding.getRoot();
    }

    @Override
    public void onCrmDataReturned() {

        crmNotesList.clear();
        crmNotesList.addAll(viewModel.getCrmNotesList());
        crmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRowClicked(CrmNotes notes) {

        //todo open crm details
    }

    @Override
    public void onInfoClicked() {

        //todo show dialog
    }
}
