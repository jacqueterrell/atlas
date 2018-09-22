package com.team.mamba.atlas.userInterface.welcome.select_business_account.admin_accounts;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.databinding.SingleTextListRowBinding;

import java.util.List;

public class AdminAccountsAdapter extends RecyclerView.Adapter<AdminAccountsAdapter.AdminAccountsViewHolder> {


    private List<UserProfile> adminProfiles;
    private AdminAccountsNavigator navigator;


    public AdminAccountsAdapter(List<UserProfile> adminProfiles, AdminAccountsNavigator navigator){

        this.adminProfiles = adminProfiles;
        this.navigator = navigator;
    }


    public class AdminAccountsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private SingleTextListRowBinding binding;

        public AdminAccountsViewHolder(SingleTextListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            UserProfile profile = adminProfiles.get(getAdapterPosition());
            navigator.onAdminProfileSelected(profile);
        }
    }

    @NonNull
    @Override
    public AdminAccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SingleTextListRowBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.single_text_list_row,parent,false);

        return new AdminAccountsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAccountsViewHolder holder, int position) {

        UserProfile profile = adminProfiles.get(position);
        String name = profile.getFirstName() + " " + profile.getLastName();

        holder.binding.tvSelection.setText(name);
    }

    @Override
    public int getItemCount() {
        return adminProfiles.size();
    }
}
