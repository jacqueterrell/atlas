package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.local.WorkHistory;
import com.team.mamba.atlas.databinding.EditEducationListRowBinding;
import com.team.mamba.atlas.databinding.EditWorkHistoryListRowBinding;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkAdapter.EditWorkViewHolder;
import java.util.List;

public class EditWorkAdapter extends RecyclerView.Adapter<EditWorkViewHolder> {


    private List<WorkHistory> workHistoryList;
    private EditWorkViewModel viewModel;


    public EditWorkAdapter(EditWorkViewModel viewModel,List<WorkHistory> workHistories){

        this.workHistoryList = workHistories;
        this.viewModel = viewModel;
    }


    public class EditWorkViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        private EditWorkHistoryListRowBinding binding;

        public EditWorkViewHolder(EditWorkHistoryListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            WorkHistory workHistory = workHistoryList.get(getAdapterPosition());
            viewModel.getNavigator().onRowClicked(workHistory,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public EditWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        EditWorkHistoryListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.edit_work_history_list_row,parent,false);

        return new EditWorkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EditWorkViewHolder holder, int position) {

        WorkHistory workHistory = workHistoryList.get(position);

        int index = position + 1;
        String entry = "Entry " + index;

        holder.binding.tvIndex.setText(entry);
        holder.binding.tvCompanyName.setText(workHistory.getCompanyName());
        holder.binding.tvTitle.setText(workHistory.getTitle());
        holder.binding.tvIndustry.setText(workHistory.getIndustry());
    }

    @Override
    public int getItemCount() {
        return workHistoryList.size();
    }
}
