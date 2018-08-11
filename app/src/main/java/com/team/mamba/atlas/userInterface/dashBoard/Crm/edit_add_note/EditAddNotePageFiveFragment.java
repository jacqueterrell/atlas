package com.team.mamba.atlas.userInterface.dashBoard.Crm.edit_add_note;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.databinding.EditAddPageFiveBinding;
import com.team.mamba.atlas.utils.ChangeFragments;

public class EditAddNotePageFiveFragment extends Fragment {


    private EditAddPageFiveBinding binding;
    private static CrmNotes notes;


    public static EditAddNotePageFiveFragment newInstance(CrmNotes crmNotes){

        notes = crmNotes;
        return new EditAddNotePageFiveFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_add_page_five,container,false);
        setListeners();

        binding.btnContinue.setOnClickListener(v -> {

            notes.setOppGoal(getOpportunityGoal());
            openPageSix(notes);
        });

        return binding.getRoot();
    }


    private void setListeners(){

        binding.chkBoxSolicitation.setOnClickListener(v -> {

            if (binding.chkBoxSolicitation.isChecked()){

                binding.chkBxNonTeaming.setChecked(false);
                binding.chkBoxDirectSell.setChecked(false);

            } else {

                binding.chkBoxSolicitation.setChecked(true);
            }
        });

        binding.chkBoxDirectSell.setOnClickListener(v -> {

            if (binding.chkBoxDirectSell.isChecked()){

                binding.chkBxNonTeaming.setChecked(false);
                binding.chkBoxSolicitation.setChecked(false);

            } else {

                binding.chkBoxDirectSell.setChecked(true);
            }
        });

        binding.chkBxNonTeaming.setOnClickListener(v -> {

            if (binding.chkBxNonTeaming.isChecked()){

                binding.chkBoxDirectSell.setChecked(false);
                binding.chkBoxSolicitation.setChecked(false);

            } else {

                binding.chkBxNonTeaming.setChecked(true);
            }
        });
    }


    private int getOpportunityGoal(){

        if (binding.chkBoxSolicitation.isChecked()){

            return 0;

        } else if (binding.chkBxNonTeaming.isChecked()){

            return 1;

        } else {

            return 2;

        }
    }

    private void openPageSix(CrmNotes crmNotes){

        ChangeFragments.addFragmentFadeIn(EditAddNotePageSixFragment.newInstance(crmNotes),getActivity().getSupportFragmentManager(),"PageSix",null);

    }
}
