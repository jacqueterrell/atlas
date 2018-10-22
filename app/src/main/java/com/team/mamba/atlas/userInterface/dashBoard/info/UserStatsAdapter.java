package com.team.mamba.atlas.userInterface.dashBoard.info;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.UserStatsListRowBinding;
import com.team.mamba.atlas.userInterface.dashBoard.info.UserStatsAdapter.UserStatsViewHolder;
import java.util.ArrayList;
import java.util.List;

public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsViewHolder> {

    private List<String> userStatsList;

    public UserStatsAdapter(List<String> userStatsList){

        this.userStatsList = userStatsList;
    }

    @NonNull
    @Override
    public UserStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        UserStatsListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.user_stats_list_row, parent, false);


        return new UserStatsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatsViewHolder holder, int position) {

        String userStat = userStatsList.get(position);
        holder.binding.tvUserStat.setText(userStat);
    }

    @Override
    public int getItemCount() {
        return userStatsList.size();
    }

    public class UserStatsViewHolder extends RecyclerView.ViewHolder{

        private UserStatsListRowBinding binding;

        public UserStatsViewHolder(UserStatsListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
