package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import java.util.List;

public interface AddEducationNavigator {


    void onRowClicked(String university);

    List<String> getPermUniversityList();
}
