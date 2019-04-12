package com.team.mamba.atlas.userInterface.dashBoard.settings.businessLogin;

import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard.settings.SettingsDataModel;
import java.util.List;

public class SettingsBusinessLoginViewModel extends BaseViewModel<SettingsBusinessLoginNavigator> {

    private SettingsBusinessLoginDataModel dataModel;
    private List<BusinessProfile> businessProfileList;

    /**************** Getters and Setters***************/

    public void setDataModel(SettingsBusinessLoginDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setBusinessProfileList(List<BusinessProfile> businessProfileList) {
        this.businessProfileList = businessProfileList;
    }

    public List<BusinessProfile> getBusinessProfileList() {
        return businessProfileList;
    }


    /**************** DataModel Requests *************/

    public void checkAllBusinesses() {
        dataModel.checkAllBusinesses(this);
    }
}