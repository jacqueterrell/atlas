package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.YoYo.AnimatorCallback;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.databinding.InfoLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import javax.inject.Inject;
import com.team.mamba.atlas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoFragment extends BaseFragment<InfoLayoutBinding,InfoViewModel>
        implements InfoNavigator {

    @Inject
    InfoViewModel viewModel;

    @Inject
    InfoDataModel dataModel;

    private InfoLayoutBinding binding;
    float barWidth = 0.65f; // x2 dataset


    public static InfoFragment newInstance(){

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         binding = getViewDataBinding();

         setLabels();
         setBarChart();

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
    }

    @Override
    public void onOpportunitiesButtonClicked() {

        binding.layoutNetworkNotSelected.setVisibility(View.VISIBLE);
        binding.layoutNetworkSelected.setVisibility(View.GONE);

        binding.layoutOpportunityNotSelected.setVisibility(View.GONE);
        binding.layoutOpportunitySelected.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddButtonClicked() {

    }

    @Override
    public void onUserProfileClicked() {

    }

    @Override
    public void onSettingsClicked() {

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

    private List<String> setLabels(){

        List<String> labels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        for (int i = 0; i < 6; i++){

            calendar.set(Calendar.MONTH, currentMonth - i);

            labels.add(getMonthsList().get(currentMonth));

            currentMonth -=1;

        }

        return labels;
    }

    private List<BarEntry> setNetworkBarValues(){

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 100));
        entries.add(new BarEntry(1, 50));
        entries.add(new BarEntry(2, 30));
        entries.add(new BarEntry(3, 10));
        entries.add(new BarEntry(4, 20));
        entries.add(new BarEntry(5, 0));

        return entries;
    }


    private void setBarChart(){

        BarDataSet dataSet = new BarDataSet(setNetworkBarValues(), "New Connections");

        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.chart_blue);

        dataSet.setColor(ContextCompat.getColor(getBaseActivity(),R.color.chart_blue));
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
        xAxis.setValueFormatter((value, axis) -> setLabels().get((int) value));

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
        binding.barChartNetwork.animateXY(1000, 1000);
        binding.barChartNetwork.getDescription().setText("");

    }

        private List<String> getMonthsList(){

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

}
