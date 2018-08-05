package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.ConnectionRecord;
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
    private static final String AUTHOR_ID = "authorID";

    private AppDataManager dataManager;
    private int companyTotal = 0;
    private int totalOpportunities = 0;

    private boolean isUserStatsSet = false;
    private boolean isUserOpportunitiesSet = false;
    private boolean isRecentActivitiesSet = false;

    private List<String> userStatsList = new ArrayList<>();
    private List<ConnectionRecord> recentActivityConnections = new ArrayList<>();
    private QuerySnapshot connectionsCollection;
    private static final int ALLOWED_TOTAL_RECENT_ACTIVITIES = 51;


    @Inject
    public InfoDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }

    public void checkAllConnections(InfoViewModel viewModel) {

        List<Integer> connectionMonths = new ArrayList<>();

        List<String> consentingIdList = new ArrayList<>();

        String userId = dataManager.getSharedPrefs().getUserId();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

                                consentingIdList.add(consentingUserId);

                                String time = document.getData().get(TIME_STAMP).toString();

                                String adjustedTime = AppFormatter.timeStampFormatter.format(Double.valueOf(time));
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.valueOf(adjustedTime) * 1000);

                                int month = calendar.get(Calendar.MONTH);
                                connectionMonths.add(month);

                            }

                        }

                        setUserStats(viewModel, consentingIdList);
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

        viewModel.setConnectionsMap(connectionsMap);
        viewModel.getNavigator().setNetworkBarChartData();
    }

    /**
     * Loops through all connections to find the total of businesses
     *
     * @param connectionIdList total list of all connections accepted
     */
    private void setUserStats(InfoViewModel viewModel, List<String> connectionIdList) {

        //clear the list before use
        if (!isUserOpportunitiesSet && !isUserStatsSet) {

            userStatsList.clear();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("businesses")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String businessId = document.getData().get("id").toString();

                            for (String id : connectionIdList) {

                                if (businessId.equals(id)) {

                                    companyTotal += 1;
                                    break;
                                }

                            }

                        }

                        String connectionsTotal = connectionIdList.size() + " Connections";
                        userStatsList.add(connectionsTotal);

                        isUserStatsSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;

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

        db.collection(AppConstants.BUS_NOTES_COLLECTION)
                .whereEqualTo(AUTHOR_ID, userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            totalOpportunities += 1;
                        }

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

                        isUserOpportunitiesSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;

                            viewModel.getNavigator().setUserStatsAdapter(userStatsList,recentActivityConnections);
                        }



                    } else {

                        viewModel.getNavigator().handlerError("Error setUserStatusOpportunities");
                        Logger.e(task.getException().getMessage());
                    }
                });

    }


    private void setRecentActivity(InfoViewModel viewModel) {

        recentActivityConnections.clear();
        String userId = dataManager.getSharedPrefs().getUserId();
        List<ConnectionRecord> completedConnections = new ArrayList<>();
        List<ConnectionRecord> newConnections = new ArrayList<>();

        for (QueryDocumentSnapshot document : connectionsCollection) {

            boolean isConfirmed = Boolean.valueOf(document.getData().get(IS_CONFIRMED).toString());
            String consentingUserId = document.getData().get(CONSENTING_USER_ID).toString();
            String requestingUserId = document.getData().get(REQUESTED_USER_ID).toString();
            String consentingUserName = document.getData().get(CONSENTING_USER_NAME).toString();
            String requestingUserName = document.getData().get(REQUESTING_USER_NAME).toString();


            String time = document.getData().get(TIME_STAMP).toString();
            String adjustedTimeStamp = AppFormatter.timeStampFormatter.format(Double.valueOf(time));

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
                                .setTimestamp(adjustedTimeStamp)
                                .setUserId(consentingUserId)
                                .build();

                        if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES){

                            completedConnections.add(record);
                        }

                    } else {

                        ConnectionRecord record = new ConnectionRecord.Builder()
                                .setName(consentingUserName)
                                .setBusiness(false)
                                .setNeedsApproval(false)
                                .setRecentActivity(false)
                                .setTimestamp(adjustedTimeStamp)
                                .setUserId(consentingUserId)
                                .build();

                        if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES){

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
                            .setTimestamp(adjustedTimeStamp)
                            .build();

                    if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES){

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
                        .setTimestamp(adjustedTimeStamp)
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

            viewModel.getNavigator().setUserStatsAdapter(userStatsList,recentActivityConnections);
        }


    }


}
