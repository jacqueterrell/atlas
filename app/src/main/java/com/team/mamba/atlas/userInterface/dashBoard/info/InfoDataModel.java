package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.ConnectionRecord;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.formatData.AppFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class InfoDataModel {

    private static final String REQUESTED_USER_ID = "requestingUserID";
    private static final String IS_CONFIRMED = "isConfirmed";
    private static final String TIME_STAMP = "timestamp";
    private static final String CONSENTING_USER_ID = "consentingUserID";
    private static final String CONSENTING_USER_NAME = "consentingUserName";
    private static final String REQUESTING_USER_NAME = "requestingUserName";
    private static final String IS_BUSINESS = "isOrgBus";
    private static final String STAGE = "stage";
    private static final String AUTHOR_ID = "authorID";

    private AppDataManager dataManager;
    private int companyTotal = 0;
    private int totalOpportunities = 0;

    private boolean isUserStatsSet = false;
    private boolean isUserOpportunitiesSet = false;
    private boolean isRecentActivitiesSet = false;
    private boolean isNetworksChartSet = false;
    private boolean isOpportunitiesChartSet = false;

    private List<String> userStatsList = new ArrayList<>();
    private List<ConnectionRecord> recentActivityConnections = new ArrayList<>();
    private List<String> confirmedConnectionsList = new ArrayList<>();

    private QuerySnapshot connectionsCollection;
    private static final int ALLOWED_TOTAL_RECENT_ACTIVITIES = 51;


    @Inject
    public InfoDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }

    public void checkAllConnections(InfoViewModel viewModel) {

        List<Integer> connectionMonths = new ArrayList<>();
        confirmedConnectionsList.clear();
        String userId = dataManager.getSharedPrefs().getUserId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (dataManager.getSharedPrefs().isBusinessAccount()){

            getBusinessDetails(viewModel);

        } else {

            getUserDetails(viewModel);
        }

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        connectionsCollection = task.getResult();

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            boolean isConfirmed = Boolean.valueOf(document.getData().get(IS_CONFIRMED).toString());
                            String consentingUserId = document.getData().get(CONSENTING_USER_ID).toString();
                            String requestingUserId = document.getData().get(REQUESTED_USER_ID).toString();

                            if (isConfirmed && requestingUserId.equals(userId)) {

                                confirmedConnectionsList.add(consentingUserId);

                                String time = document.getData().get(TIME_STAMP).toString();

                                String adjustedTime = AppFormatter.timeStampFormatter.format(Double.valueOf(time));
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.valueOf(adjustedTime) * 1000);

                                int month = calendar.get(Calendar.MONTH);
                                connectionMonths.add(month);

                            }

                        }

                        setUserStats(viewModel);
                        setUserStatusOpportunities(viewModel);
                        setConnectionsMap(viewModel, connectionMonths);
                        setRecentActivity(viewModel);

                    } else {

                        viewModel.getNavigator().handlerError("");
                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }
                });


    }

    private void setConnectionsMap(InfoViewModel viewModel, List<Integer> connectionMonths) {

        Map<Integer, Integer> connectionsMap = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        for (int i = 0; i < 6; i++) {

            calendar.set(Calendar.MONTH, currentMonth - i);

            connectionsMap.put(currentMonth, 0); //7,6,5,4,3,2
            currentMonth -= 1;

        }

        for (Integer months : connectionMonths) {

            for (Map.Entry<Integer, Integer> entry : connectionsMap.entrySet()) {

                int monthKey = entry.getKey();
                int count = entry.getValue();

                if (monthKey == months) {

                    entry.setValue(count + 1);
                }
            }

        }

        isNetworksChartSet = true;
        viewModel.setNetworksMap(connectionsMap);

        if (isNetworksChartSet && isOpportunitiesChartSet){

            viewModel.getNavigator().setNetworkBarChartData();
        }

    }

    /**
     * Loops through all connections to find the total of businesses
     *
     */
    private void setUserStats(InfoViewModel viewModel) {

        //clear the list before use
        if (!isUserOpportunitiesSet && !isUserStatsSet) {

            userStatsList.clear();
            companyTotal = 0;
            totalOpportunities = 0;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("businesses")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String businessId = document.getData().get("id").toString();

                            for (String id : confirmedConnectionsList) {

                                if (businessId.equals(id)) {

                                    companyTotal += 1;
                                    break;
                                }

                            }

                        }

                        isUserStatsSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;
                            setUserStatsTotal();

                            viewModel.getNavigator().setUserStatsAdapter(userStatsList,recentActivityConnections);
                        }


                    } else {

                        viewModel.getNavigator().handlerError("Error setUserStats");
                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }
                });

    }

    /**
     * Looks through Bus Notes to find Opportunities total
     */
    private void setUserStatusOpportunities(InfoViewModel viewModel) {

        //clear the list before use
        if (!isUserOpportunitiesSet && !isUserStatsSet) {

            userStatsList.clear();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = dataManager.getSharedPrefs().getUserId();
        List<Integer> opportunityStageNumbers = new ArrayList<>();

        db.collection(AppConstants.BUS_NOTES_COLLECTION)
                .whereEqualTo(AUTHOR_ID, userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            int stage = Integer.valueOf(document.getData().get(STAGE).toString());

                            opportunityStageNumbers.add(stage);
                            totalOpportunities += 1;
                        }

                        setOpportunitiesMap(viewModel,opportunityStageNumbers);

                        isUserOpportunitiesSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;
                            setUserStatsTotal();

                            viewModel.getNavigator().setUserStatsAdapter(userStatsList,recentActivityConnections);
                        }



                    } else {

                        viewModel.getNavigator().handlerError("Error setUserStatusOpportunities");
                        Logger.e(task.getException().getMessage());
                    }
                });

    }


    private void setOpportunitiesMap(InfoViewModel viewModel, List<Integer> opportunityStages) {

        Map<Integer, Integer> connectionsMap = new LinkedHashMap<>();

        for (int i = 0; i < 5; i++) {

            connectionsMap.put(i, 0); //7,6,5,4,3,2
        }

        for (Integer stage : opportunityStages) {

            for (Map.Entry<Integer, Integer> entry : connectionsMap.entrySet()) {

                int stageKey = entry.getKey();
                int count = entry.getValue();

                if (stageKey == stage) {

                    entry.setValue(count + 1);
                }
            }

        }

        isOpportunitiesChartSet = true;
        viewModel.setOpportunitiesMap(connectionsMap);

        if (isNetworksChartSet && isOpportunitiesChartSet){

            viewModel.getNavigator().setNetworkBarChartData();
        }

    }


    private void setUserStatsTotal(){

        String connectionsTotal = confirmedConnectionsList.size() + " Connections";
        userStatsList.add(connectionsTotal);


        if (companyTotal == 1) {

            String company = companyTotal + " Company";
            userStatsList.add(company);

        } else {

            String company = companyTotal + " Companies";
            userStatsList.add(company);
        }


        if (totalOpportunities == 1) {

            String opportunities = totalOpportunities + " Opportunity";
            userStatsList.add(opportunities);

        } else {

            String opportunities = totalOpportunities + " Opportunities";
            userStatsList.add(opportunities);
        }

    }


    private void setRecentActivity(InfoViewModel viewModel) {

        recentActivityConnections.clear();
        String userId = dataManager.getSharedPrefs().getUserId();
        List<ConnectionRecord> completedConnections = new ArrayList<>();
        List<ConnectionRecord> newConnections = new ArrayList<>();

        List<String> confirmedUsers = new ArrayList<>();

        for (QueryDocumentSnapshot document : connectionsCollection) {

            boolean isConfirmed = Boolean.valueOf(document.getData().get(IS_CONFIRMED).toString());
            String consentingUserId = document.getData().get(CONSENTING_USER_ID).toString();
            String requestingUserId = document.getData().get(REQUESTED_USER_ID).toString();
            String consentingUserName = document.getData().get(CONSENTING_USER_NAME).toString();
            String requestingUserName = document.getData().get(REQUESTING_USER_NAME).toString();


            String time = document.getData().get(TIME_STAMP).toString();
            String adjustedTimeStamp = AppFormatter.timeStampFormatter.format(Double.valueOf(time));
            long systemTimeStamp = Long.parseLong(adjustedTimeStamp);

            //get all completed connections
            if (isConfirmed && requestingUserId.equals(userId)){ //The connection has been confirmed

                try {

                    boolean isBusiness = Boolean.valueOf(document.getData().get(IS_BUSINESS).toString());

                    if (isBusiness){

                        ConnectionRecord record = new ConnectionRecord.Builder()
                                .setName(consentingUserName)
                                .setBusiness(true)
                                .setNeedsApproval(false)
                                .setRecentActivity(false)
                                .setTimestamp(systemTimeStamp)
                                .setUserId(consentingUserId)
                                .build();

                        if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES && !confirmedUsers.contains(consentingUserName)){

                            confirmedUsers.add(consentingUserName);
                            completedConnections.add(record);
                        }

                    } else {

                        ConnectionRecord record = new ConnectionRecord.Builder()
                                .setName(consentingUserName)
                                .setBusiness(false)
                                .setNeedsApproval(false)
                                .setRecentActivity(false)
                                .setTimestamp(systemTimeStamp)
                                .setUserId(consentingUserId)
                                .build();

                        if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES && !confirmedUsers.contains(consentingUserName)){

                            confirmedUsers.add(consentingUserName);
                            completedConnections.add(record);
                        }
                    }

                } catch (Exception e){

                    ConnectionRecord record = new ConnectionRecord.Builder()
                            .setName(consentingUserName)
                            .setUserId(consentingUserId)
                            .setBusiness(false)
                            .setNeedsApproval(false)
                            .setRecentActivity(false)
                            .setTimestamp(systemTimeStamp)
                            .build();

                    if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES && !confirmedUsers.contains(consentingUserName)){

                        confirmedUsers.add(consentingUserName);
                        completedConnections.add(record);
                    }
                }

            } else if (!isConfirmed && consentingUserId.equals(userId)){ //The connection is new and needs to be approved

                ConnectionRecord newRecord = new ConnectionRecord.Builder()
                        .setName(requestingUserName)
                        .setUserId(requestingUserId)
                        .setBusiness(false)
                        .setNeedsApproval(true)
                        .setRecentActivity(false)
                        .setTimestamp(systemTimeStamp)
                        .build();

                if (newConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES){

                    newConnections.add(newRecord);
                }
            }

        }

        isRecentActivitiesSet = true;

        recentActivityConnections.addAll(completedConnections);
        recentActivityConnections.addAll(newConnections);

        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet) {

            isUserStatsSet = false;
            isUserOpportunitiesSet = false;
            isRecentActivitiesSet = false;
            setUserStatsTotal();

            viewModel.getNavigator().setUserStatsAdapter(userStatsList,recentActivityConnections);
        }


    }

    private void getBusinessDetails(InfoViewModel viewModel){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String savedUserId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .whereEqualTo("id", savedUserId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Logger.i("email:" + document.getData().get("email"));
                            String userId = document.getData().get("id").toString();
                            String name = document.getData().get("name").toString();
                            String userCode = document.getData().get("code").toString();
                            String email = document.getData().get("email").toString();

                            viewModel.setUserCode(userCode);
                            viewModel.getNavigator().setUserDetails();
                            dataManager.getSharedPrefs().setUserCode(userCode);
                        }

                    }
                });
    }



    public void getUserDetails(InfoViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String savedUserId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.USERS_COLLECTION)
                .whereEqualTo("id", savedUserId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String userId = document.getData().get("id").toString();
                            String first = document.getData().get("firstName").toString();
                            String last = document.getData().get("lastName").toString();
                            String phoneNumber = document.getData().get("phone").toString();
                            String dob = document.getData().get("dob").toString();
                            String userCode = document.getData().get("code").toString();

                            String adjustedTime = AppFormatter.timeStampFormatter.format(Double.valueOf(dob));

                            viewModel.setUserCode(userCode);
                            dataManager.getSharedPrefs().setUserCode(userCode);
                            viewModel.getNavigator().setUserDetails();

                            return;

                        }


                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }

}
