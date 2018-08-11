package com.team.mamba.atlas.userInterface.dashBoard.Crm.edit_add_note;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.databinding.EditAddPageOneBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailFragment;
import com.team.mamba.atlas.utils.ChangeFragments;

public class EditAddNotePageOneFragment extends Fragment {



    private static CrmNotes notes;
    private EditAddPageOneBinding binding;

    public static EditAddNotePageOneFragment newInstance(CrmNotes crmNotes){

        notes = crmNotes;
        return new EditAddNotePageOneFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_add_page_one,container,false);

        binding.btnContinue.setOnClickListener(v -> {

            if (allValuesCompleted()){

                notes.setNoteName(binding.etName.getText().toString());
                notes.setPoc(binding.etPointOfContact.getText().toString());
                notes.setWhereMetCitySt(binding.etWhere.getText().toString());

                ChangeFragments.addFragmentFadeIn(EditAddNotePageTwoFragment.newInstance(notes),getActivity().getSupportFragmentManager(),"PageTwo",null);
            }

        });

        showSoftKeyboard(binding.etName);

        binding.setCrmNote(notes);

        return binding.getRoot();
    }

    private boolean allValuesCompleted(){

        String opportunityName = binding.etName.getText().toString();
        String primaryContact = binding.etPointOfContact.getText().toString();
        String location = binding.etWhere.getText().toString();

        if (opportunityName.isEmpty()
                || primaryContact.isEmpty()
                || location.isEmpty()){

            Snackbar.make(binding.getRoot(), "Please enter all values to continue", Snackbar.LENGTH_LONG)
                    .show();

            return false;

        } else {

            return true;
        }
    }

    private void showSoftKeyboard(View view) {

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }

}
