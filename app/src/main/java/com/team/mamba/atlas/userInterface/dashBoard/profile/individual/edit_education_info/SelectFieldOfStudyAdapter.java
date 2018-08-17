package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.SingleTextListRowBinding;

import java.util.List;

public class SelectFieldOfStudyAdapter extends RecyclerView.Adapter<SelectFieldOfStudyAdapter.FieldOfStudyViewHolder> {


    private List<String> fieldOfStudyList;
    private AddEducationNavigator navigator;


    public SelectFieldOfStudyAdapter(List<String> fieldOfStudyList,AddEducationNavigator navigator) {

        this.fieldOfStudyList = fieldOfStudyList;
        this.navigator = navigator;
    }

    @NonNull
    @Override
    public FieldOfStudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleTextListRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.single_text_list_row,parent,false);

        return new FieldOfStudyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldOfStudyViewHolder holder, int position) {

        String fieldOfStudy = fieldOfStudyList.get(position);

        holder.binding.tvSelection.setText(fieldOfStudy);
    }

    @Override
    public int getItemCount() {
        return fieldOfStudyList.size();
    }


    public class FieldOfStudyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SingleTextListRowBinding binding;

        public FieldOfStudyViewHolder(SingleTextListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String fieldOfStudy = fieldOfStudyList.get(getAdapterPosition());
            navigator.onFieldOfStudyRowClicked(fieldOfStudy);

        }
    }

    /**
     * Used by a QueryTextListener defined in {@link AddEducationFragment}
     *
     * @param text
     */
    public void filter(String text){

        fieldOfStudyList.clear();

        if (text.equals("")){

            fieldOfStudyList.addAll(navigator.getPermFieldOfStudyList());

        } else {

            text = text.toLowerCase();
            for (String fieldOfStudy : navigator.getPermFieldOfStudyList()){

                if (fieldOfStudy.toLowerCase().contains(text)){

                    fieldOfStudyList.add(fieldOfStudy);
                }
            }
        }

        notifyDataSetChanged();
    }
}
