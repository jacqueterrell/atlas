package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.data.model.local.Education;
import com.team.mamba.atlas.databinding.AddEducationLayoutBinding;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.utils.CollegesArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddEducationFragment extends Fragment implements AddEducationNavigator,SearchView.OnQueryTextListener {

    private AddEducationLayoutBinding binding;
    private static EditEducationNavigator navigator;
    private DashBoardActivity parentActivity;
    private SelectSchoolAdapter selectSchoolAdapter;
    private List<String> universityList = new ArrayList<>();
    private List<String> permUniversityList = new ArrayList<>();

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

        getActivity().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);

        permUniversityList.addAll(Arrays.asList(CollegesArray.colleges));
        universityList.addAll(Arrays.asList(CollegesArray.colleges));
        selectSchoolAdapter = new SelectSchoolAdapter(universityList,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(selectSchoolAdapter);

        binding.btnSave.setOnClickListener(v -> onSavedClicked());

        if (navigator.isEditing()){

            Education education = navigator.getEditingEducation();
            String degree = education.getDegree();
            String school = education.getSchool();
            String fieldOfStudy = education.getFieldOfStudy();

            binding.etDegree.setText(degree);
            binding.etSchool.setText(school);
            binding.etFieldOfStudy.setText(fieldOfStudy);
        }

        setUpListeners();
        setUpUniversitySearchView();

        return binding.getRoot();
    }

    @Override
    public List<String> getPermUniversityList() {
        return permUniversityList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (binding.searchViewSchool.hasFocus()){

            selectSchoolAdapter.filter(newText);
        }

        return true;
    }

    private void setUpListeners(){


        binding.searchViewSchool.setOnQueryTextFocusChangeListener((v, hasFocus) -> {

            if (hasFocus){

                universityList.clear();
                universityList.addAll(Arrays.asList(CollegesArray.colleges));
                binding.recyclerView.setAdapter(selectSchoolAdapter);
            }
        });

        binding.etDegree.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus){

                universityList.clear();
                binding.recyclerView.setAdapter(selectSchoolAdapter);

            }
        });

        binding.etFieldOfStudy.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus){

                universityList.clear();
                binding.recyclerView.setAdapter(selectSchoolAdapter);

            }
        });
    }

    @Override
    public void onRowClicked(String university) {

      //  binding.searchViewSchool.set;
        binding.searchViewSchool.setQuery(university,false);
        binding.searchViewSchool.clearFocus();
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


    private void setUpUniversitySearchView(){

        binding.searchViewSchool.setOnQueryTextListener(this);
        binding.searchViewSchool.setIconifiedByDefault(false);
        binding.searchViewSchool.setFocusable(false);

        //set the color for our search view edit text and text hint
        EditText searchEditText = binding.searchViewSchool.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(getResources().getColor(R.color.material_icons_light));
        searchEditText.setHint("School");
        searchEditText.setGravity(Gravity.CENTER);

        //set the color for our search view icon
        ImageView searchMagIcon = binding.searchViewSchool.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        searchMagIcon.setVisibility(View.GONE);

        //set the line color
        View v = binding.searchViewSchool.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);
    }
}
