package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.data.model.TestProfile;
import com.team.mamba.atlas.data.model.UserConnections;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.utils.AppConstants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class InfoDataModel {


    private static final String STAGE = "stage";
    private static final String AUTHOR_ID = "authorID";

    private AppDataManager dataManager;
    private int companyTotal = 0;
    private int totalOpportunities = 0;
    private String userRanking = "";

    private boolean isUserStatsSet = false;
    private boolean isUserOpportunitiesSet = false;
    private boolean isRecentActivitiesSet = false;
    public boolean isUserDetailsSaved = false;
    private boolean isNetworksChartSet = false;
    private boolean isOpportunitiesChartSet = false;

    private List<String> userStatsList = new ArrayList<>();
    private List<UserConnections> recentActivityConnections = new ArrayList<>();
    private List<UserProfile> individualConnections = new ArrayList<>();
    private List<String> companyUserIds = new ArrayList<>();
    private List<String> confirmedConnectionsList = new ArrayList<>();

    private List<Integer> allUserScores = new ArrayList<>();
    private int totalNumberOfUsers;

    private static final int ALLOWED_TOTAL_RECENT_ACTIVITIES = 51;


    @Inject
    public InfoDataModel(AppDataManager dataManager) {

        this.dataManager = dataManager;
    }

    public void checkAllConnections(InfoViewModel viewModel) {

        List<Integer> connectionMonths = new ArrayList<>();
        confirmedConnectionsList.clear();
        companyUserIds.clear();

        if (dataManager.getSharedPrefs().isBusinessAccount()) {

            isUserDetailsSaved = true;
        }

        String userId = dataManager.getSharedPrefs().getUserId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> userConnections = task.getResult().toObjects(UserConnections.class);

                        viewModel.setUserConnections(userConnections);

                        for (UserConnections connection : viewModel.getUserConnections()) {

                            if (connection.isConfirmed() && connection.getRequestingUserID().equals(userId)) {

                                confirmedConnectionsList.add(connection.getConsentingUserID());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(connection.getTimestamp() * 1000);
                                int month = calendar.get(Calendar.MONTH);
                                connectionMonths.add(month);

                                if (connection.isOrgBus) {

                                    companyUserIds.add(connection.getId());
                                }
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

        if (isNetworksChartSet && isOpportunitiesChartSet) {

            viewModel.getNavigator().setNetworkBarChartData();
        }

    }

    /**
     * Loops through all connections to find the total of businesses
     */
    private void setUserStats(InfoViewModel viewModel) {

        //clear the list before use
        if (!isUserOpportunitiesSet && !isUserStatsSet) {

            userStatsList.clear();
            companyTotal = 0;
            totalOpportunities = 0;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String savedUserId = dataManager.getSharedPrefs().getUserId();

        db.collection(AppConstants.BUSINESSES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<BusinessProfile> businessProfiles = task.getResult().toObjects(BusinessProfile.class);

                        for (BusinessProfile profile : businessProfiles) {

                            if (profile.getId().equals(savedUserId)) {

                                viewModel.setBusinessProfile(profile);

                            }

                            for (String id : confirmedConnectionsList) {

                                if (profile.getId().equals(id)) {

                                    companyTotal += 1;
                                    break;
                                }

                            }
                        }

                        isUserStatsSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet && isUserDetailsSaved) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;
                            isUserDetailsSaved = false;
                            setUserStatsTotal(viewModel);

                            viewModel.getNavigator().setUserStatsAdapter(userStatsList, recentActivityConnections);
                        }

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
            companyTotal = 0;
            totalOpportunities = 0;
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

                        setOpportunitiesMap(viewModel, opportunityStageNumbers);

                        isUserOpportunitiesSet = true;

                        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet && isUserDetailsSaved) {

                            isUserStatsSet = false;
                            isUserOpportunitiesSet = false;
                            isRecentActivitiesSet = false;
                            isUserDetailsSaved = false;
                            setUserStatsTotal(viewModel);

                            viewModel.getNavigator().setUserStatsAdapter(userStatsList, recentActivityConnections);
                        }

                        if (!dataManager.getSharedPrefs().isBusinessAccount()) {

                            getUserDetails(viewModel);

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

        if (isNetworksChartSet && isOpportunitiesChartSet) {

            viewModel.getNavigator().setNetworkBarChartData();
        }

    }


    private void setUserStatsTotal(InfoViewModel viewModel) {

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

        if (!dataManager.getSharedPrefs().isBusinessAccount()){

            userStatsList.add(userRanking);
        }

    }


    private void setRecentActivity(InfoViewModel viewModel) {

        recentActivityConnections.clear();
        String userId = dataManager.getSharedPrefs().getUserId();
        List<UserConnections> completedConnections = new ArrayList<>();
        List<UserConnections> newConnections = new ArrayList<>();

        List<String> confirmedUsers = new ArrayList<>();

        for (UserConnections record : viewModel.getUserConnections()) {

            //get all completed connections
            if (record.isConfirmed() && record.getRequestingUserID()
                    .equals(userId)) { //The connection has been confirmed

                if (record.isBusiness()) {

                    //the users is the requester
                    if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES
                            && !confirmedUsers.contains(record.getConsentingUserName())) {

                        confirmedUsers.add(record.getConsentingUserName());
                        completedConnections.add(record);

                    }


                } else {

                    record.setRecentActivity(false);

                    //our user is the requester
                    if (completedConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES
                            && !confirmedUsers.contains(record.getConsentingUserName())) {

                        confirmedUsers.add(record.getConsentingUserName());
                        completedConnections.add(record);

                    }


                }


            } else if (!record.isConfirmed()
                    && record.getConsentingUserID().equals(userId)) { //The connection is new and needs to be approved

                record.setNeedsApproval(true);

                if (newConnections.size() < ALLOWED_TOTAL_RECENT_ACTIVITIES) {

                    newConnections.add(record);
                }
            }

        }

        isRecentActivitiesSet = true;

        recentActivityConnections.addAll(completedConnections);
        recentActivityConnections.addAll(newConnections);

        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet && isUserDetailsSaved) {

            isUserStatsSet = false;
            isUserOpportunitiesSet = false;
            isRecentActivitiesSet = false;
            isUserDetailsSaved = false;
            setUserStatsTotal(viewModel);

            viewModel.getNavigator().setUserStatsAdapter(userStatsList, recentActivityConnections);
        }


    }


    public void getUserDetails(InfoViewModel viewModel) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        individualConnections.clear();
        String savedUserId = dataManager.getSharedPrefs().getUserId();
        List<String> connectionIds = new ArrayList<>();

        db.collection(AppConstants.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        isUserDetailsSaved = true;

                        List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                        totalNumberOfUsers = userProfiles.size();

                        //save the selected user profile
                        for (UserProfile profile : userProfiles) {

                            allUserScores.add(profile.getScore());

                            try {
                                if (profile.getId().equals(savedUserId)) {

                                    viewModel.setUserProfile(profile);

                                    //get all of the user's connections by id
                                    for (Map.Entry<String, String> entry : profile.getConnections().entrySet()) {

                                        String userId = entry.getKey();

                                        if (!companyUserIds.contains(userId)) {

                                            connectionIds.add(userId);
                                        }
                                    }

                                }
                            } catch (Exception e) {

                                Logger.e("Invalid profile setup " + e.getLocalizedMessage());
                            }
                        }

                        //This loops through all of the user's individual(non business) contacts and pulls their
                        //contacts and adds them to a list.
                        for (UserProfile profile : userProfiles) {

                            for (String connectionId : connectionIds) {

                                try {
                                    if (profile.getId().equals(connectionId)) {

                                        for (Map.Entry<String, String> entry : profile.getConnections().entrySet()) {

                                            String userId = entry.getKey();

                                            individualConnections.add(profile);
                                        }
                                    }
                                } catch (Exception e) {

                                    Logger.e("Invalid profile setup " + e.getLocalizedMessage());
                                }
                            }
                        }

                        calculateAllUsersScore(viewModel);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }

                });

    }

    private void calculateAllUsersScore(InfoViewModel viewModel) {

        int totalScoresLowerThanUsers = 0;
        DecimalFormat df2 = new DecimalFormat(".##");

        /*****Get total individual users****/

        int connections = 2 * confirmedConnectionsList.size();
        int compTotal = 3 * companyTotal;
        int oppTotal = 5 * totalOpportunities;
        int algorithmTotal = connections + compTotal + oppTotal + individualConnections.size();

        for (Integer score : allUserScores) {

            if (score < algorithmTotal) {

                totalScoresLowerThanUsers += 1;
            }
        }

        double finalPercentile = (double) totalScoresLowerThanUsers / totalNumberOfUsers * 100;

        if (finalPercentile > 80) {

            userRanking = "All-Star";

        } else if (finalPercentile > 60) {

            userRanking = "Expert";

        } else if (finalPercentile > 40) {

            userRanking = "Advanced";

        } else if (finalPercentile > 20) {

            userRanking =  "Intermediate";

        } else {

            userRanking = "Beginner";
        }

        Logger.i("total score" + algorithmTotal);
        Logger.i("final score" + finalPercentile);

        if (isUserStatsSet && isUserOpportunitiesSet && isRecentActivitiesSet && isUserDetailsSaved) {

            isUserStatsSet = false;
            isUserOpportunitiesSet = false;
            isRecentActivitiesSet = false;
            isUserDetailsSaved = false;
            setUserStatsTotal(viewModel);

            viewModel.getNavigator().setUserStatsAdapter(userStatsList, recentActivityConnections);
        }

    }

}
