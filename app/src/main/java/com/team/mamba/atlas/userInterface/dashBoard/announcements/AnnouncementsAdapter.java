package com.team.mamba.atlas.userInterface.dashBoard.announcements;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.Announcements;
import com.team.mamba.atlas.databinding.AnnouncementsListRowBinding;
import com.team.mamba.atlas.userInterface.dashBoard.announcements.AnnouncementsAdapter.NotificationsViewHolder;
import java.util.Collections;
import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<NotificationsViewHolder>{


    private List<Announcements> announcementsList;


    public AnnouncementsAdapter(List<Announcements> announcements){

        this.announcementsList = announcements;
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder{

        private AnnouncementsListRowBinding binding;

        public NotificationsViewHolder(AnnouncementsListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        AnnouncementsListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.announcements_list_row,parent,false);

        return new NotificationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        Announcements announcements = announcementsList.get(position);

        holder.binding.tvBusinessName.setText(announcements.getOrgBusName());
        holder.binding.tvMessage.setText(announcements.getText());
        holder.binding.tvDate.setText(announcements.getDateToString());

        if (announcements.isEvent){

            holder.binding.ivEvent.setVisibility(View.VISIBLE);
            holder.binding.ivNotification.setVisibility(View.GONE);

        } else {

            holder.binding.ivEvent.setVisibility(View.GONE);
            holder.binding.ivNotification.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }
}
