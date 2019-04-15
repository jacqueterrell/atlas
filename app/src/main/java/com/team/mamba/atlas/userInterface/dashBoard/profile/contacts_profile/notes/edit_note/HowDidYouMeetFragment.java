package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes.edit_note;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.databinding.HowDidYouMeetBinding;
import com.team.mamba.atlas.data.model.api.fireStore.PersonalNotes;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.utils.ChangeFragments;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class HowDidYouMeetFragment extends BaseFragment<HowDidYouMeetBinding,HowDidYouMeetViewModel>
 implements HowDidYouMeetNavigator{



    @Inject
    HowDidYouMeetViewModel viewModel;

    @Inject
    Context appContext;

    private HowDidYouMeetBinding binding;
    private static PersonalNotes personalNotes;
    private static UserProfile contactProfile;
    private static final String CHANGE_ENCOUNTER = "Chance Encounter";
    private static final String  WORK_EVENT = "Work Event";
    private static final String SOCIAL_EVENT = "Social Event";
    private static final String PEER_INTRO = "Peer Introduction";
    private static final String PEER_COLLEAGUE = "Peer/Colleague";


    public static HowDidYouMeetFragment newInstance(UserProfile userProfile,PersonalNotes notes){
        personalNotes = notes;
        contactProfile = userProfile;
       return new HowDidYouMeetFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;

    }

    @Override
    public int getLayoutId() {
        return R.layout.how_did_you_meet;
    }

    @Override
    public HowDidYouMeetViewModel getViewModel() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = getViewDataBinding();

        setUpListeners();
        setData();
        return binding.getRoot();
    }

    @Override
    public void onContinueClicked() {

        PersonalNotes notes = new PersonalNotes();
        notes.setAuthorID(dataManager.getSharedPrefs().getUserId());
        notes.setHowMet(getHowMet());
        notes.setWhereMet(binding.etWhere.getText().toString());
        notes.setSubjectID(contactProfile.getId());
        hideKeyboard();

        if (personalNotes != null && !personalNotes.getDetails().isEmpty()){
            notes.setDetails(personalNotes.getDetails());
        } else {
            List<String> details = new ArrayList<>();
            details.add("");
            notes.setDetails(details);
        }

        ChangeFragments.addFragmentFadeIn(PersonalNoteRecyclerView
                .newInstance(notes,dataManager), getActivity().getSupportFragmentManager(), "ContactInfoDialog", null);

    }

    private void setData(){
        if (personalNotes != null){
            String whereMet = personalNotes.getWhereMet();
            binding.etWhere.setText(whereMet);
            String howMet = personalNotes.getHowMet();

            switch (howMet) {
                case CHANGE_ENCOUNTER:
                    binding.chkBoxChanceEncounter.performClick();
                    break;
                case WORK_EVENT:
                    binding.chkBoxWorkEvent.performClick();
                    break;
                case SOCIAL_EVENT:
                    binding.chkBoxSocialEvent.performClick();
                    break;
                case PEER_INTRO:
                    binding.chkBoxPeerIntroduction.performClick();
                    break;
                default:
                    binding.chkBoxPeerColleague.performClick();
                    break;
            }
        }
    }


    private void setUpListeners(){

        //chance encounter
        binding.chkBoxChanceEncounter.setOnClickListener(view -> {

            if (binding.chkBoxChanceEncounter.isChecked()) {

                binding.chkBoxWorkEvent.setChecked(false);
                binding.chkBoxSocialEvent.setChecked(false);
                binding.chkBoxPeerIntroduction.setChecked(false);
                binding.chkBoxPeerColleague.setChecked(false);

            } else {

                binding.chkBoxChanceEncounter.setChecked(true);
            }

        });

        //work event
        binding.chkBoxWorkEvent.setOnClickListener(view -> {

            if (binding.chkBoxWorkEvent.isChecked()) {

                binding.chkBoxChanceEncounter.setChecked(false);
                binding.chkBoxSocialEvent.setChecked(false);
                binding.chkBoxPeerIntroduction.setChecked(false);
                binding.chkBoxPeerColleague.setChecked(false);

            } else {

                binding.chkBoxWorkEvent.setChecked(true);
            }

        });

        //social event
        binding.chkBoxSocialEvent.setOnClickListener(view -> {

            if (binding.chkBoxChanceEncounter.isChecked()) {

                binding.chkBoxWorkEvent.setChecked(false);
                binding.chkBoxChanceEncounter.setChecked(false);
                binding.chkBoxPeerIntroduction.setChecked(false);
                binding.chkBoxPeerColleague.setChecked(false);

            } else {

                binding.chkBoxSocialEvent.setChecked(true);
            }

        });

        //peer introduction
        binding.chkBoxPeerIntroduction.setOnClickListener(view -> {

            if (binding.chkBoxPeerIntroduction.isChecked()) {

                binding.chkBoxWorkEvent.setChecked(false);
                binding.chkBoxSocialEvent.setChecked(false);
                binding.chkBoxChanceEncounter.setChecked(false);
                binding.chkBoxPeerColleague.setChecked(false);

            } else {

                binding.chkBoxPeerIntroduction.setChecked(true);
            }

        });

        //peer colleague
        binding.chkBoxPeerColleague.setOnClickListener(view -> {

            if (binding.chkBoxPeerColleague.isChecked()) {

                binding.chkBoxWorkEvent.setChecked(false);
                binding.chkBoxSocialEvent.setChecked(false);
                binding.chkBoxPeerIntroduction.setChecked(false);
                binding.chkBoxChanceEncounter.setChecked(false);

            } else {

                binding.chkBoxPeerColleague.setChecked(true);
            }

        });
    }

    private String getHowMet(){

        if (binding.chkBoxChanceEncounter.isChecked()){
            return CHANGE_ENCOUNTER;

        } else if (binding.chkBoxWorkEvent.isChecked()){
            return WORK_EVENT;

        } else if (binding.chkBoxSocialEvent.isChecked()){
            return SOCIAL_EVENT;

        } else if (binding.chkBoxPeerIntroduction.isChecked()){
            return PEER_INTRO;

        } else {
            return PEER_COLLEAGUE;
        }
    }
}
