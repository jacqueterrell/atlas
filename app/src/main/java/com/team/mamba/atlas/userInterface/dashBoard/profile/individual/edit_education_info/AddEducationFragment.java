package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

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
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.data.model.local.Education;
import com.team.mamba.atlas.databinding.AddEducationLayoutBinding;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddEducationFragment extends Fragment {

    private AddEducationLayoutBinding binding;
    private static EditEducationNavigator navigator;
    private DashBoardActivity parentActivity;

    public static AddEducationFragment newInstance(EditEducationNavigator editEducationNavigator){

        navigator = editEducationNavigator;
        return new AddEducationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (DashBoardActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_education_layout,container,false);


        binding.btnSave.setOnClickListener(v -> {

                onSavedClicked();

        });

        if (navigator.isEditing()){

            Education education = navigator.getEditingEducation();
            String degree = education.getDegree();
            String school = education.getSchool();
            String fieldOfStudy = education.getFieldOfStudy();

            binding.etDegree.setText(degree);
            binding.etSchool.setText(school);
            binding.etFieldOfStudy.setText(fieldOfStudy);
        }

        return binding.getRoot();
    }


    private void onSavedClicked() {

        String degree = binding.etDegree.getText().toString();
        String field = binding.etFieldOfStudy.getText().toString();
        String school = binding.etSchool.getText().toString();

        if (isAllValuesValid()) {

            Education education = new Education();
            education.setDegree(degree);
            education.setFieldOfStudy(field);
            education.setSchool(school);

           //add to list and close
            if (navigator.isEditing()){

                navigator.onEditRowSaved(education);

            } else {

                navigator.onSaveNewEducation(education);
            }

            parentActivity.onBackPressed();

        } else {


            Snackbar.make(binding.getRoot(), "Please enter all values", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private boolean isAllValuesValid(){

        String degree = binding.etDegree.getText().toString();
        String field = binding.etFieldOfStudy.getText().toString();
        String school = binding.etSchool.getText().toString();

        if (degree.isEmpty() || field.isEmpty() || school.isEmpty()){

            return false;
        }

        return true;
    }
}
