package com.team.mamba.atlas.userInterface.dashBoard.crm.edit_add_note;

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
import com.team.mamba.atlas.data.model.api.fireStore.CrmNotes;
import com.team.mamba.atlas.databinding.EditAddPageTwoBinding;
import com.team.mamba.atlas.utils.ChangeFragments;

public class EditAddNotePageTwoFragment  extends Fragment {


    private static CrmNotes notes;
    private EditAddPageTwoBinding binding;


    public static EditAddNotePageTwoFragment newInstance(CrmNotes crmNotes){

        notes = crmNotes;
        return new EditAddNotePageTwoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_add_page_two,container,false);

        setListeners();
        showSoftKeyboard(binding.etEvent);

        binding.btnContinue.setOnClickListener(v -> {

            String event = binding.etEvent.getText().toString();

            if (event.isEmpty()){

                Snackbar.make(binding.getRoot(), "Please enter an event name to continue", Snackbar.LENGTH_LONG)
                        .show();
            } else {

                notes.setWhereMetEventName(event);
                notes.setHowMet(getHowMetToInteger());
                ChangeFragments.addFragmentFadeIn(EditAddNotePageThreeFragment.newInstance(notes),getActivity().getSupportFragmentManager(),"PageThree",null);
            }
        });

        binding.setCrmNote(notes);
        setCachedData();

        return binding.getRoot();
    }


    private void showSoftKeyboard(View view) {

        if (view.requestFocus()) {

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    private void setListeners(){

        binding.chkBoxExhibit.setOnClickListener(v -> {

            if (binding.chkBoxExhibit.isChecked()){

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxMeeting.setChecked(false);

            } else {

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(true);
            }

        });

        binding.layoutExhibit.setOnClickListener(v -> {

            if (binding.chkBoxExhibit.isChecked()){

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxMeeting.setChecked(false);

            } else {

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(true);
            }
        });



        binding.chkBoxMeeting.setOnClickListener(v -> {

            if (binding.chkBoxMeeting.isChecked()){

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);

            } else {

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);
                binding.chkBoxMeeting.setChecked(true);
            }
        });

        binding.layoutMeeting.setOnClickListener(v -> {

            if (binding.chkBoxMeeting.isChecked()){

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);

            } else {

                binding.chkBoxNetworking.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);
                binding.chkBoxMeeting.setChecked(true);
            }
        });


        binding.chkBoxNetworking.setOnClickListener(v -> {

            if (binding.chkBoxNetworking.isChecked()){

                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);

            } else {

                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);
                binding.chkBoxNetworking.setChecked(true);
            }
        });

        binding.layoutNetworking.setOnClickListener(v -> {

            if (binding.chkBoxNetworking.isChecked()){

                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);

            } else {

                binding.chkBoxMeeting.setChecked(false);
                binding.chkBoxExhibit.setChecked(false);
                binding.chkBoxNetworking.setChecked(true);
            }
        });

    }

    public int getHowMetToInteger(){

        if (binding.chkBoxExhibit.isChecked()){

            return 0;

        } else if (binding.chkBoxNetworking.isChecked()){

            return 1;

        } else {

            return 2;

        }
    }

    private void setCachedData(){


        binding.etEvent.setText(notes.getWhereMetEventName());

        if (notes.getHowMet() == 0){

            binding.chkBoxExhibit.setChecked(true);
            binding.chkBoxNetworking.setChecked(false);
            binding.chkBoxMeeting.setChecked(false);

        } else if (notes.getHowMet() == 1){

            binding.chkBoxExhibit.setChecked(false);
            binding.chkBoxNetworking.setChecked(true);
            binding.chkBoxMeeting.setChecked(false);

        } else {

            binding.chkBoxExhibit.setChecked(false);
            binding.chkBoxNetworking.setChecked(false);
            binding.chkBoxMeeting.setChecked(true);
        }
    }
}
