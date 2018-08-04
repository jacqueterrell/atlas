package com.team.mamba.atlas.userInterface.dashBoard.info;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.formatData.AppFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private static final String AUTHOR_ID = "authorID";

    private AppDataManager dataManager;
    private int companyTotal =0;
    private int totalOpportunities = 0;


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
                .whereEqualTo(REQUESTED_USER_ID, userId)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            boolean isConfirmed = Boolean.valueOf(document.getData().get(IS_CONFIRMED).toString());
                            String consentingUserId = document.getData().get(CONSENTING_USER_ID).toString();

                            if (isConfirmed) {

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
                        setConnectionsMap(viewModel, connectionMonths);

                    } else {

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

    private void setUserStats(InfoViewModel viewModel, List<String> connectionIdList) {

        List<String> userStatsList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("businesses")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String businessId = document.getData().get("id").toString();

                            for (String id : connectionIdList){

                                if (businessId.equals(id)){

                                    companyTotal += 1;
                                    break;
                                }

                            }

                        }


                        String connectionsTotal = connectionIdList.size() + " Connections";
                        userStatsList.add(connectionsTotal);

                        setUserStatusOpportunities(viewModel,userStatsList);

                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }
                });

    }

    private void setUserStatusOpportunities(InfoViewModel viewModel,  List<String> userStatsList){

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

                        if (companyTotal == 1){

                            String company = companyTotal + " Company";
                            userStatsList.add(company);

                        } else {

                            String company = companyTotal + " Companies";
                            userStatsList.add(company);
                        }

                        if (totalOpportunities == 1){

                            String opportunities = totalOpportunities + " Opportunity";
                            userStatsList.add(opportunities);

                        } else {

                            String opportunities = totalOpportunities + " Opportunities";
                            userStatsList.add(opportunities);
                        }

                        viewModel.getNavigator().setUserStatsAdapter(userStatsList);


                    } else {

                        Logger.e(task.getException().getMessage());
                        task.getException().printStackTrace();
                    }
                });


    }


}
