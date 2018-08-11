package com.team.mamba.atlas.userInterface.dashBoard.Crm.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.local.CrmFilter;
import com.team.mamba.atlas.databinding.FilterCrmBinding;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;

public class CrmFilterSettingsFragment extends Fragment {


    private DashBoardActivity parentActivity;
    private FilterCrmBinding binding;
    private CrmFilter savedFilter;
    private CrmFilter newFilter = new CrmFilter();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (DashBoardActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.filter_crm,container,false);

        setUpListeners();

        binding.ivCancel.setOnClickListener(v -> {

            CrmFilter filter = new CrmFilter();
            filter.setNextStep(-1);
            filter.setOpportunity(-1);
            filter.setStatus(-1);

            parentActivity.setCrmFilter(filter);
            parentActivity.onBackPressed();
        });

        binding.ivSubmit.setOnClickListener(v -> {

            if (savedFilter != null){

                parentActivity.setCrmFilter(savedFilter);

            } else {

                CrmFilter filter = new CrmFilter();
                filter.setNextStep(-1);
                filter.setOpportunity(-1);
                filter.setStatus(-1);

                parentActivity.setCrmFilter(filter);
            }

            parentActivity.onBackPressed();

        });

        return binding.getRoot();
    }

    private void setUpListeners(){


        binding.seekBarStatus.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

                Logger.i("clicked");
                newFilter.setStatus(seekParams.thumbPosition);
                savedFilter = newFilter;

            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked");
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked" + seekBar.getTickCount());

            }
        });

        //opportunities
        binding.seekBarOpportunity.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

                Logger.i("clicked");
                newFilter.setOpportunity(seekParams.thumbPosition);
                savedFilter = newFilter;

            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked");
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked");
            }
        });

        //next step
        binding.seekBarNextStep.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

                Logger.i("clicked");
                newFilter.setNextStep(seekParams.thumbPosition);
                savedFilter = newFilter;

            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked");
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {

                Logger.i("clicked");
            }
        });
    }
}
