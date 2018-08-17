package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import java.util.List;

public interface AddEducationNavigator {


    void onSchoolRowClicked(String university);

    void onDegreeRowClicked(String degree);

    void onFieldOfStudyRowClicked(String fieldOfStudy);

    List<String> getPermUniversityList();

    List<String> getPermDegreeList();

    List<String> getPermFieldOfStudyList();

}
