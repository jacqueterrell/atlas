package com.team.mamba.atlas.userInterface.dashBoard.announcements;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.Announcements;
import com.team.mamba.atlas.databinding.AnnouncementsLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class AnnouncementsFragment extends BaseFragment<AnnouncementsLayoutBinding, AnnouncementsViewModel>
        implements AnnouncementsNavigator {


    @Inject
    AnnouncementsViewModel viewModel;

    @Inject
    AnnouncementsDataModel dataModel;

    private AnnouncementsLayoutBinding binding;
    private List<Announcements> announcementsList = new ArrayList<>();
    private AnnouncementsAdapter announcementsAdapter;
    private DashBoardActivity parentActivity;
    private DashBoardActivityNavigator parentNavigator;

    public static AnnouncementsFragment newInstance() {

        return new AnnouncementsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.announcements_layout;
    }

    @Override
    public AnnouncementsViewModel getViewModel() {
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
        parentActivity = (DashBoardActivity) context;
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

        announcementsAdapter = new AnnouncementsAdapter(announcementsList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(announcementsAdapter);

        binding.swipeContainer.setOnRefreshListener(() -> viewModel.requestAnnouncements(getViewModel()));

        if (!viewModel.getAnnouncementsList().isEmpty()){

            onAnnouncementsReturned();
        }

        viewModel.requestAnnouncements(getViewModel());

        setUpToolBar();

        return binding.getRoot();
    }

    @Override
    public void onAnnouncementsReturned() {

        announcementsList.clear();
        announcementsList.addAll(viewModel.getAnnouncementsList());

        Collections.sort(announcementsList,(o1,o2) -> Double.compare(o2.getAdjustedTimeStamp(), o1.getAdjustedTimeStamp()));

        announcementsAdapter.notifyDataSetChanged();
        binding.swipeContainer.setRefreshing(false);
    }

    @Override
    public void handleError(String errorMsg) {

        showSnackbar(errorMsg);
        binding.swipeContainer.setRefreshing(false);
    }

    private void setUpToolBar(){

        parentNavigator.showToolBar();
        parentActivity.hideCrmIcon();
        parentActivity.hideContactsIcon();
        parentActivity.hideInfoIcon();
        parentActivity.showNotificationsIcon();
    }
}
