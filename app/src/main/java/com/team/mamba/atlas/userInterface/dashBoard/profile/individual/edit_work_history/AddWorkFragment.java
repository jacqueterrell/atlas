package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history;

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
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.local.WorkHistory;
import com.team.mamba.atlas.databinding.AddWorkHistoryLayoutBinding;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;

public class AddWorkFragment extends Fragment {


    private AddWorkHistoryLayoutBinding binding;
    private static EditWorkNavigator navigator;
    private DashBoardActivity parentActivity;

    public static AddWorkFragment newInstance(EditWorkNavigator editWorkNavigator){

        navigator = editWorkNavigator;
        return new AddWorkFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (DashBoardActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.add_work_history_layout,container,false);

        binding.btnSave.setOnClickListener(v -> {

            onSavedClicked();

        });

        if (navigator.isEditing()){

            WorkHistory workHistory = navigator.getEditingWork();
            String title = workHistory.getTitle();
            String industry = workHistory.getIndustry();
            String companyName = workHistory.getCompanyName();

            binding.etTitle.setText(title);
            binding.etIndustry.setText(industry);
            binding.etCompanyName.setText(companyName);
        }

        return binding.getRoot();
    }


    private void onSavedClicked(){

        String title = binding.etTitle.getText().toString();
        String industry = binding.etIndustry.getText().toString();
        String companyName = binding.etCompanyName.getText().toString();

        if (isAllValid(title,industry,companyName)){

            WorkHistory workHistory = new WorkHistory();
            workHistory.setCompanyName(companyName);
            workHistory.setTitle(title);
            workHistory.setIndustry(industry);

            if (navigator.isEditing()){

                navigator.onEditRowSaved(workHistory);

            } else {

                navigator.onSaveNewWorkHistory(workHistory);
            }

            parentActivity.onBackPressed();

        } else {

            Snackbar.make(binding.getRoot(),"Please enter all values",Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    private boolean isAllValid(String title,String industry,String companyName){

        if (title.isEmpty() || industry.isEmpty() || companyName.isEmpty()){

          return false;

        }

        return true;
    }
}
