package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_education_info;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.SingleTextListRowBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectDegreeAdapter extends RecyclerView.Adapter<SelectDegreeAdapter.SelectDegreeViewHolder> {


    private List<String> degreeList = new ArrayList<>();
    private AddEducationNavigator navigator;

    public SelectDegreeAdapter(List<String> degreeList,AddEducationNavigator navigator) {
        this.degreeList = degreeList;
        this.navigator = navigator;
    }

    @NonNull
    @Override
    public SelectDegreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleTextListRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.single_text_list_row,parent,false);

        return new SelectDegreeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectDegreeViewHolder holder, int position) {

        String degree = degreeList.get(position);
        holder.binding.tvSelection.setText(degree);
    }

    @Override
    public int getItemCount() {
        return degreeList.size();
    }

    public class SelectDegreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SingleTextListRowBinding binding;

        public SelectDegreeViewHolder(SingleTextListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String degree = degreeList.get(getAdapterPosition());
            navigator.onDegreeRowClicked(degree);
        }
    }

    /**
     * Used by a QueryTextListener defined in {@link AddEducationFragment}
     *
     * @param text
     */
    public void filter(String text){

        degreeList.clear();

        if (text.equals("")){

            degreeList.addAll(navigator.getPermUniversityList());

        } else {

            text = text.toLowerCase();
            for (String degree : navigator.getPermDegreeList()){

                if (degree.toLowerCase().contains(text)){

                    degreeList.add(degree);
                }
            }
        }

        notifyDataSetChanged();
    }
}
