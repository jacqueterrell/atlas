package com.team.mamba.atlas.userInterface.dashBoard.Crm.edit_add_note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.data.model.api.CrmNotes;

public class EditAddNotePageOneFragment extends Fragment {



    private static CrmNotes notes;

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



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
