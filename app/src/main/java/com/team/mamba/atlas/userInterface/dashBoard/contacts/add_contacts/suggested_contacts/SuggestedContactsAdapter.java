package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.FindUsersListRowBinding;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.suggested_contacts.SuggestedContactsAdapter.SuggestedContactsViewHolder;
import java.util.List;

public class SuggestedContactsAdapter extends RecyclerView.Adapter<SuggestedContactsViewHolder> {

    private List<UserProfile> userProfileList;
    private SuggestedContactsViewModel viewModel;


    public SuggestedContactsAdapter(SuggestedContactsViewModel viewModel,List<UserProfile> userProfiles){

        this.viewModel = viewModel;
        this.userProfileList = userProfiles;
    }

    public class SuggestedContactsViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        private FindUsersListRowBinding binding;

        public SuggestedContactsViewHolder(FindUsersListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            UserProfile profile = userProfileList.get(getAdapterPosition());
            viewModel.getNavigator().onUsersRowClicked(profile);
        }
    }


    @NonNull
    @Override
    public SuggestedContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FindUsersListRowBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.find_users_list_row,parent,false);

        return new SuggestedContactsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedContactsViewHolder holder, int position) {

        UserProfile profile = userProfileList.get(position);
        String name = profile.getFirstName() + " " + profile.getLastName();

        holder.binding.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return userProfileList.size();
    }
}
