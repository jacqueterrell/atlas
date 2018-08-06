package com.team.mamba.atlas.userInterface.dashBoard._container_activity.add_user;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

public class AddUserViewModel extends BaseViewModel<AddUserNavigator> {



    /*****Our App Logic*****/

    public boolean isLastNameValid(String lastName){

        if (lastName.isEmpty()){

            return false;

        } else {

            return true;
        }
    }

    public boolean isUserCodeValid(String userCode){

        if (userCode.isEmpty()){

            return false;

        } else {

            return true;
        }
    }

    /*****On Click Listeners*****/
    public void onNextButtonClicked(){

        getNavigator().onNextButtonClicked();
    }
}
