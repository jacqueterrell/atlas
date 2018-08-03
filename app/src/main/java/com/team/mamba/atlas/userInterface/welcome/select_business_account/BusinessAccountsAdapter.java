package com.team.mamba.atlas.userInterface.welcome.select_business_account;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.BusinessAccountsListRowBinding;

import java.util.List;

public class BusinessAccountsAdapter extends RecyclerView.Adapter<BusinessAccountsAdapter.BusinessViewHolder> {


    private List<String> namesList;
    private BusinessAccountsViewModel viewModel;


    public BusinessAccountsAdapter(BusinessAccountsViewModel viewModel, List<String> namesList){

        this.namesList = namesList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        BusinessAccountsListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.business_accounts_list_row, parent, false);


        return new BusinessViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {

        String name = namesList.get(position);

        holder.binding.btnAccountName.setText(name);

        holder.binding.btnAccountName.setOnClickListener(view -> viewModel.getNavigator().getSelectedPosition(position));
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {

         BusinessAccountsListRowBinding binding;

        public BusinessViewHolder(BusinessAccountsListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
