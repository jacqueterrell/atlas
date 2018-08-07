package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.data.model.ConnectionRecord;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InfoViewModel extends BaseViewModel<InfoNavigator> {

    private InfoDataModel dataModel;
    private Map<Integer,Integer> networksMap = new LinkedHashMap<>();
    private Map<Integer,Integer> opportunitiesMap = new LinkedHashMap<>();
    private boolean networkChartSelected = true;
    private UserProfile userProfile;
    private BusinessProfile businessProfile;
    private List<ConnectionRecord> connectionRecordList;



    /***************view logic************/


    /***************getters and setters************/

    public void setDataModel(InfoDataModel dashBoardHomeDataModel) {
        this.dataModel = dashBoardHomeDataModel;
    }

    public void setNetworksMap(Map<Integer,Integer> connectionsMap) {
        this.networksMap = connectionsMap;
    }

    public Map<Integer,Integer> getNetworksMap() {
        return networksMap;
    }

    public void setOpportunitiesMap(Map<Integer, Integer> opportunitiesMap) {
        this.opportunitiesMap = opportunitiesMap;
    }

    public Map<Integer, Integer> getOpportunitiesMap() {
        return opportunitiesMap;
    }

    public void setNetworkChartSelected(boolean networkChartSelected) {
        this.networkChartSelected = networkChartSelected;
    }

    public boolean isNetworkChartSelected() {
        return networkChartSelected;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setConnectionRecordList(List<ConnectionRecord> connectionRecordList) {
        this.connectionRecordList = connectionRecordList;
    }

    public List<ConnectionRecord> getConnectionRecordList() {
        return connectionRecordList;
    }

    /***************onclick listeners************/

    public void onNetworkButtonClicked(){

        getNavigator().onNetworkButtonClicked();
    }

    public void onOpportunitiesButtonClicked(){

        getNavigator().onOpportunitiesButtonClicked();
    }

    public void onAddButtonClicked(){

        getNavigator().onAddContactClicked();
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
