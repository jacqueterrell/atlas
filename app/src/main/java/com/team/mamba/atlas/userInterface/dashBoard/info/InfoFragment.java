package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.data.model.UserConnections;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.databinding.InfoLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import java.util.Collections;
import java.util.Map;
import javax.inject.Inject;
import com.team.mamba.atlas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoFragment extends BaseFragment<InfoLayoutBinding, InfoViewModel>
        implements InfoNavigator {

    @Inject
    InfoViewModel viewModel;

    @Inject
    InfoDataModel dataModel;

    private InfoLayoutBinding binding;
    float barWidth = 0.65f; // x2 dataset
    private static List<String> userStatsList = new ArrayList<>();
    private UserStatsAdapter userStatsAdapter;
    private RecentActivitiesAdapter recentActivitiesAdapter;
    private static List<UserConnections> recentActivityConnections = new ArrayList<>();
    private DashBoardActivityNavigator parentNavigator;


    public static InfoFragment newInstance() {

        return new InfoFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.info_layout;
    }

    @Override
    public InfoViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        parentNavigator = (DashBoardActivityNavigator) context;
        parentNavigator.showToolBar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();

        viewModel.checkAllConnections(getViewModel());

        userStatsAdapter = new UserStatsAdapter(userStatsList);
        binding.recyclerUserStats.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerUserStats.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerUserStats.setAdapter(userStatsAdapter);

        recentActivitiesAdapter = new RecentActivitiesAdapter(getViewModel(), recentActivityConnections);
        binding.recyclerRecentActivity.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerRecentActivity.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerRecentActivity.setAdapter(recentActivitiesAdapter);

        binding.swipeContainerRecentActiviy.setOnRefreshListener(() -> {

            viewModel.checkAllConnections(getViewModel());

        });

        binding.swipeContainerUserStats.setOnRefreshListener(() -> {

            viewModel.checkAllConnections(getViewModel());

        });

        return binding.getRoot();
    }

    @Override
    public void onUserStatsInfoClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogUserStatisticsInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogUserStatisticsInfo);
    }

    @Override
    public void hideUserStatusInfoDialog() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogUserStatisticsInfo.setVisibility(View.GONE))
                .playOn(binding.dialogUserStatisticsInfo);
    }

    @Override
    public void onRecentActivityInfoClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogRecentActivityInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogRecentActivityInfo);
    }

    @Override
    public void hideRecentActivityInfoDialog() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogRecentActivityInfo.setVisibility(View.GONE))
                .playOn(binding.dialogRecentActivityInfo);
    }

    @Override
    public void onNetworkButtonClicked() {

        binding.layoutNetworkNotSelected.setVisibility(View.GONE);
        binding.layoutNetworkSelected.setVisibility(View.VISIBLE);

        binding.layoutOpportunityNotSelected.setVisibility(View.VISIBLE);
        binding.layoutOpportunitySelected.setVisibility(View.GONE);

        viewModel.setNetworkChartSelected(true);

        if (viewModel.getNetworksMap().isEmpty()){

            viewModel.checkAllConnections(getViewModel());

        } else {

            setNetworksBarChart();
        }

    }

    @Override
    public void onOpportunitiesButtonClicked() {

        binding.layoutNetworkNotSelected.setVisibility(View.VISIBLE);
        binding.layoutNetworkSelected.setVisibility(View.GONE);

        binding.layoutOpportunityNotSelected.setVisibility(View.GONE);
        binding.layoutOpportunitySelected.setVisibility(View.VISIBLE);

        viewModel.setNetworkChartSelected(false);

        if (viewModel.getOpportunitiesMap().isEmpty()){

            viewModel.checkAllConnections(getViewModel());

        } else {

            setOpportunitiesBarChart();
        }

    }

    @Override
    public void onAddContactClicked() {

        parentNavigator.openAddContactDialog();
    }

    @Override
    public void onUserProfileClicked() {

        if (dataManager.getSharedPrefs().isBusinessAccount()){

            parentNavigator.openBusinessProfile();

        } else {

            parentNavigator.openUserProfile();
        }
    }

    @Override
    public void onSettingsClicked() {

        parentNavigator.openSettingsScreen();
    }

    @Override
    public void onAddressBookClicked() {

    }

    @Override
    public void onCrmClicked() {

    }

    @Override
    public void onNotificationsClicked() {

    }

    @Override
    public void setUserStatsAdapter(List<String> userStats, List<UserConnections> connectionRecords) {


        if (dataManager.getSharedPrefs().isBusinessAccount()){

            binding.tvUserId.setText(viewModel.getBusinessProfile().getCode());

        } else {

            binding.tvUserId.setText(viewModel.getUserProfile().getCode());
        }

        Collections.sort(connectionRecords,(o1,o2) -> Double.compare(o2.getTimestamp(), o1.getTimestamp()));

        userStatsList.clear();
        userStatsList.addAll(userStats);
        recentActivityConnections.clear();
        recentActivityConnections.addAll(connectionRecords);

        userStatsAdapter.notifyDataSetChanged();
        recentActivitiesAdapter.notifyDataSetChanged();
        hideSplashScreen();
        binding.swipeContainerUserStats.setRefreshing(false);
        binding.swipeContainerRecentActiviy.setRefreshing(false);

        UserProfile profile = viewModel.getUserProfile();
        parentNavigator.setUserProfile(profile);

    }


    @Override
    public void handlerError(String msg) {

        showSnackbar(msg);
        hideSplashScreen();
        binding.swipeContainerUserStats.setRefreshing(false);
        binding.swipeContainerRecentActiviy.setRefreshing(false);
    }

    @Override
    public void setNetworkBarChartData() {

        if (viewModel.isNetworkChartSelected()){

            setNetworksBarChart();

        } else {

            setOpportunitiesBarChart();

        }
    }


    private List<String> setNetworkLabels() {

        List<String> labels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        for (int i = 0; i < 6; i++) {

            calendar.set(Calendar.MONTH, currentMonth - i);

            labels.add(getMonthsList().get(currentMonth));

            currentMonth -= 1;

        }

        return labels;
    }

    private List<BarEntry> setNetworkBarValues() {

        List<BarEntry> entries = new ArrayList<>();

        int count = 0;

        for (Map.Entry<Integer, Integer> entry : viewModel.getNetworksMap().entrySet()) {

            entries.add(new BarEntry(count, entry.getValue()));
            count += 1;
        }

        return entries;
    }

    private List<String> setOpportunitiesLabels(){

        List<String> labels = new ArrayList<>();

        labels.add("New");
        labels.add("Qualified");
        labels.add("Proposal");
        labels.add("Negotiation");
        labels.add("Closed");

        return labels;
    }

    private List<BarEntry> setOpportunitiesBarValues() {

        List<BarEntry> entries = new ArrayList<>();

        int count = 0;

        for (Map.Entry<Integer, Integer> entry : viewModel.getOpportunitiesMap().entrySet()) {

            entries.add(new BarEntry(count, entry.getValue()));
            count += 1;
        }

        return entries;
    }


    private void setNetworksBarChart() {

        BarDataSet dataSet = new BarDataSet(setNetworkBarValues(), "New Connections");

        dataSet.setColor(ContextCompat.getColor(getBaseActivity(), R.color.chart_blue));
        dataSet.setValueTextColor(R.color.black);
        dataSet.setValueTextSize(14f);
        dataSet.setValueFormatter(new LargeValueFormatter());

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth);

        //X-axis
        XAxis xAxis = binding.barChartNetwork.getXAxis();
        xAxis.setTextColor(R.color.black);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisLineColor(R.color.black);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter((value, axis) -> {
            if (setNetworkLabels().size() > (int) value) {
                return setNetworkLabels().get((int) value);
            } else return null;
        });
        //Y-axis
        YAxis rightAxis = binding.barChartNetwork.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        YAxis leftAxis = binding.barChartNetwork.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);
        leftAxis.setDrawLabels(false);

        binding.barChartNetwork.setFitBars(true);
        binding.barChartNetwork.setData(data);

        binding.barChartNetwork.getLegend().setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.black));
        binding.barChartNetwork.invalidate();
        binding.barChartNetwork.animateXY(1000, 1000);
        binding.barChartNetwork.getDescription().setText("");
        binding.barChartNetwork.setClickable(false);

        binding.barChartNetwork.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        binding.barChartNetwork.animateXY(1000, 1000);
        handler.postDelayed(() -> binding.barChartNetwork.setVisibility(View.VISIBLE),400);

    }

    private void setOpportunitiesBarChart() {

        BarDataSet dataSet = new BarDataSet(setOpportunitiesBarValues(), "Opportunities");

        dataSet.setColor(ContextCompat.getColor(getBaseActivity(), R.color.chart_red));
        dataSet.setValueTextColor(R.color.black);
        dataSet.setValueTextSize(14f);
        dataSet.setValueFormatter(new LargeValueFormatter());

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth);

        //X-axis
        XAxis xAxis = binding.barChartNetwork.getXAxis();
        xAxis.setTextColor(R.color.black);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisLineColor(R.color.black);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter((value, axis) -> {
            if (setOpportunitiesLabels().size() > (int) value) {
                return setOpportunitiesLabels().get((int) value);
            } else return null;
        });
        //Y-axis
        YAxis rightAxis = binding.barChartNetwork.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        YAxis leftAxis = binding.barChartNetwork.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);
        leftAxis.setDrawLabels(false);

        binding.barChartNetwork.setData(data);
        binding.barChartNetwork.setFitBars(true);
        binding.barChartNetwork.getLegend().setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.black));
        binding.barChartNetwork.getDescription().setText("");
        binding.barChartNetwork.setClickable(false);

        binding.barChartNetwork.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        binding.barChartNetwork.animateXY(1000, 1000);
        handler.postDelayed(() -> binding.barChartNetwork.setVisibility(View.VISIBLE),400);

    }

    private List<String> getMonthsList() {

        List<String> monthsList = new ArrayList<>();

        monthsList.add("Jan");
        monthsList.add("Feb");
        monthsList.add("Mar");
        monthsList.add("Apr");
        monthsList.add("May");
        monthsList.add("Jun");
        monthsList.add("Jul");
        monthsList.add("Aug");
        monthsList.add("Sep");
        monthsList.add("Oct");
        monthsList.add("Nov");
        monthsList.add("Dec");

        return monthsList;
    }


    private void hideSplashScreen() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.layoutSplashScreen.setVisibility(View.GONE))
                .playOn(binding.layoutSplashScreen);
    }

}
