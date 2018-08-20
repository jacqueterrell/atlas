package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.ContactInfoDialogBinding;

public class ContactInfoDialog extends Fragment {


    private ContactInfoDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.contact_info_dialog,container,false);

        binding.dialogConnectionInfo.setOnClickListener(view -> {

            getActivity().onBackPressed();
        });

        return binding.getRoot();
    }
}
