package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.databinding.RecentActivitiesListRowBinding;
import com.team.mamba.atlas.userInterface.dashBoard.info.RecentActivitiesAdapter.RecentActivitiesHolder;

import java.util.List;

public class RecentActivitiesAdapter extends RecyclerView.Adapter<RecentActivitiesHolder> {

    private List<UserConnections> connectionRecords;
    private InfoViewModel viewModel;

    public RecentActivitiesAdapter(InfoViewModel viewModel, List<UserConnections> connectionRecords){

        this.viewModel = viewModel;
        this.connectionRecords = connectionRecords;
    }

    @NonNull
    @Override
    public RecentActivitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        RecentActivitiesListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.recent_activities_list_row, parent, false);

        return new RecentActivitiesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivitiesHolder holder, int position) {

        UserConnections record = connectionRecords.get(position);

        holder.binding.tvUserStat.setText(record.getConsentingUserName());

        if (record.isBusiness()){ //confirmed business record

            holder.binding.ivConfirmedConnectionBusiness.setVisibility(View.VISIBLE);
            holder.binding.ivConfirmedConnectionIndividual.setVisibility(View.GONE);
            holder.binding.ivAddNewConnection.setVisibility(View.GONE);
            holder.binding.ivRecentUpdatedAccount.setVisibility(View.GONE);

        } else if (!record.isBusiness() && !record.isNeedsApproval() && !record.isRecentActivity()){ //confirmed individual record

            holder.binding.ivConfirmedConnectionBusiness.setVisibility(View.GONE);
            holder.binding.ivConfirmedConnectionIndividual.setVisibility(View.VISIBLE);
            holder.binding.ivAddNewConnection.setVisibility(View.GONE);
            holder.binding.ivRecentUpdatedAccount.setVisibility(View.GONE);

        } else if (record.isNeedsApproval()){ //new connection to be approved

            holder.binding.ivConfirmedConnectionBusiness.setVisibility(View.GONE);
            holder.binding.ivConfirmedConnectionIndividual.setVisibility(View.GONE);
            holder.binding.ivAddNewConnection.setVisibility(View.VISIBLE);
            holder.binding.ivRecentUpdatedAccount.setVisibility(View.GONE);

        } else if (record.isRecentActivity()){ //new connection to be approved

            holder.binding.ivConfirmedConnectionBusiness.setVisibility(View.GONE);
            holder.binding.ivConfirmedConnectionIndividual.setVisibility(View.GONE);
            holder.binding.ivAddNewConnection.setVisibility(View.GONE);
            holder.binding.ivRecentUpdatedAccount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return connectionRecords.size();
    }

    public class RecentActivitiesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecentActivitiesListRowBinding binding;

        public RecentActivitiesHolder(RecentActivitiesListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            UserConnections record = connectionRecords.get(getAdapterPosition());
            viewModel.getNavigator().onRecentActivitiesRowClicked(record);

        }
    }
}