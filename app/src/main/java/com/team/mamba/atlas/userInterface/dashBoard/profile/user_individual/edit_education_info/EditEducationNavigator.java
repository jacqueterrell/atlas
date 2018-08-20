package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_education_info;

import com.team.mamba.atlas.data.model.local.Education;

public interface EditEducationNavigator {


        void onContinueClicked();
        void onSaveAndCloseClicked();
        void onEditRowSaved(Education education);
        void onProfileUpdated();
        void onSaveNewEducation(Education education);
        void onAddNewEducation();
        void onRowClicked(Education education, int pos);
        boolean isEditing();
        Education getEditingEducation();
}
