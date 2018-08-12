package com.team.mamba.atlas.userInterface.dashBoard.Crm.selected_crm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.databinding.SelectedCrmLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard.Crm.edit_add_note.EditAddNotePageOneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.Crm.main.CrmViewModel;
import com.team.mamba.atlas.utils.ChangeFragments;

import javax.inject.Inject;

public class SelectedCrmFragment extends BaseFragment<SelectedCrmLayoutBinding,SelectedCrmViewModel>
implements SelectedCrmNavigator{


    @Inject
    SelectedCrmViewModel viewModel;

    @Inject
    SelectedCrmDataModel dataModel;

    private SelectedCrmLayoutBinding binding;
    private static CrmNotes notes;
    public static boolean isNoteDeleted = false;

    public static SelectedCrmFragment newInstance(CrmNotes crmNotes){

        notes = crmNotes;
        return new SelectedCrmFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.selected_crm_layout;
    }

    @Override
    public SelectedCrmViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
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

         binding.setCrmNote(notes);

         return binding.getRoot();
    }

    @Override
    public void onSuccess() {

        CrmViewModel.crmNotesList.clear();
        hideProgressSpinner();
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onEditClicked() {

        ChangeFragments.replaceFragmentVertically(EditAddNotePageOneFragment.newInstance(notes,false),getBaseActivity().getSupportFragmentManager(),"SelectedCrm",null);

    }

    @Override
    public void onDeleteClicked() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());


        String title = "Delete " + notes.getNoteName() + "?";
        String msg = "This will permanently remove " + notes.getNoteName() + " from your opportunity list";
        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("No", (paramDialogInterface, paramInt) -> {

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {

                    showProgressSpinner();
                    viewModel.deleteNote(getViewModel(),notes);
                });

        dialog.show();
    }
}
