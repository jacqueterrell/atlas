package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InfoViewModel extends BaseViewModel<InfoNavigator> {

    private InfoDataModel dataModel;
    private Map<Integer,Integer> connectionsMap = new LinkedHashMap<>();




    /***************view logic************/


    /***************getters and setters************/

    public void setDataModel(InfoDataModel dashBoardHomeDataModel) {
        this.dataModel = dashBoardHomeDataModel;
    }

    public void setConnectionsMap(Map<Integer,Integer> connectionsMap) {
        this.connectionsMap = connectionsMap;
    }

    public Map<Integer,Integer> getConnectionsMap() {
        return connectionsMap;
    }


    /***************onclick listeners************/

    public void onNetworkButtonClicked(){

        getNavigator().onNetworkButtonClicked();
    }

    public void onOpportunitiesButtonClicked(){

        getNavigator().onOpportunitiesButtonClicked();
    }

    public void onAddButtonClicked(){

        getNavigator().onAddButtonClicked();
    }

    public void onUserProfileClicked(){

        getNavigator().onUserProfileClicked();
    }

    public void onSettingsClicked(){

        getNavigator().onSettingsClicked();
    }

    public void onUserStatsInfoClicked(){

        getNavigator().onUserStatsInfoClicked();
    }

    public void onRecentActivityInfoClicked(){

        getNavigator().onRecentActivityInfoClicked();
    }

    public void hideRecentActivityInfoDialog(){

        getNavigator().hideRecentActivityInfoDialog();
    }

    public void hideUserStatusInfoDialog(){

        getNavigator().hideUserStatusInfoDialog();
    }

    /********* Datamodel Calls********/

    public void checkAllConnections(InfoViewModel viewModel){

        dataModel.checkAllConnections(viewModel);
    }
}
