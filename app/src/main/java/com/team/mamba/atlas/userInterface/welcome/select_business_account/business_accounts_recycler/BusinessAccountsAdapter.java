package com.team.mamba.atlas.userInterface.welcome.select_business_account.business_accounts_recycler;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessAccountsListRowBinding;

import java.util.List;

public class BusinessAccountsAdapter extends RecyclerView.Adapter<BusinessAccountsAdapter.BusinessViewHolder> {


    private List<BusinessProfile> businessProfileList;
    private BusinessAccountsViewModel viewModel;


    public BusinessAccountsAdapter(BusinessAccountsViewModel viewModel, List<BusinessProfile> businessProfiles){

        this.businessProfileList = businessProfiles;
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

        BusinessProfile profile = businessProfileList.get(position);

        holder.binding.btnAccountName.setText(profile.getName());

        holder.binding.btnAccountName.setOnClickListener(view -> viewModel.getNavigator().onAccountSelected(profile));
    }

    @Override
    public int getItemCount() {
        return businessProfileList.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {

         BusinessAccountsListRowBinding binding;

        public BusinessViewHolder(BusinessAccountsListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
