package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.userInterface.dashBoard.info.RecentActivitiesAdapter.RecentActivitiesHolder;

public class RecentActivitiesAdapter extends RecyclerView.Adapter<RecentActivitiesHolder> {

    public RecentActivitiesAdapter(){


    }

    @NonNull
    @Override
    public RecentActivitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivitiesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecentActivitiesHolder extends RecyclerView.ViewHolder{


        public RecentActivitiesHolder(View itemView) {
            super(itemView);
        }
    }
}
