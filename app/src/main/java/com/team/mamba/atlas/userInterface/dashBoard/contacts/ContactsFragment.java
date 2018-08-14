package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.ContactsLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;

import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.ContactListAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ContactsFragment extends BaseFragment<ContactsLayoutBinding, ContactsViewModel>
        implements ContactsNavigator, SearchView.OnQueryTextListener {


    @Inject
    ContactsViewModel viewModel;

    @Inject
    ContactsDataModel dataModel;

    private ContactsLayoutBinding binding;
    private DashBoardActivityNavigator parentNavigator;
    private DashBoardActivity parentActivity;
    private static List<UserConnections> userConnectionsList = new ArrayList<>();
    private ContactListAdapter contactListAdapter;


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.contacts_layout;
    }

    @Override
    public ContactsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return binding.progressSpinner;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentNavigator = (DashBoardActivityNavigator) context;
        parentActivity = (DashBoardActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        viewModel.setDataModel(dataModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();

        contactListAdapter = new ContactListAdapter(userConnectionsList, getViewModel());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(contactListAdapter);

        binding.swipeContainer.setOnRefreshListener(() -> viewModel.requestContactsInfo(getViewModel()));

        if (viewModel.getUserConnectionsList().isEmpty()){

            showProgressSpinner();
            viewModel.requestContactsInfo(getViewModel());

        } else {

                if (!viewModel.getSavedUserId().equals(dataManager.getSharedPrefs().getUserId())){

                    showProgressSpinner();
                    viewModel.requestContactsInfo(getViewModel());

                } else {

                    viewModel.requestContactsInfo(getViewModel());
                }

        }

        setUpToolBar();
        setUpSearchView();
        return binding.getRoot();
    }

    @Override
    public void onAddNewContactClicked() {

        parentActivity.openAddContactDialog();
    }

    @Override
    public void onSyncContactsClicked() {

    }

    @Override
    public void onSettingsClicked() {

        parentActivity.openSettingsScreen();
    }

    @Override
    public void onProfileImageClicked() {

        parentActivity.openUserProfile(viewModel.getUserProfile());
    }

    @Override
    public void onGroupFilterClicked() {

    }

    @Override
    public void onIndividualFilterClicked() {

    }

    @Override
    public void onRowClicked(UserConnections userConnections) {

        if (userConnections.isOrgBus) {

            parentNavigator.openBusinessProfile(userConnections.getBusinessProfile());

        } else {

            parentNavigator.openUserProfile(userConnections.getUserProfile());
        }

        hideProgressSpinner();

    }

    @Override
    public void onDataValuesReturned() {

        List<UserConnections> individualConnections = new ArrayList<>();
        List<UserConnections> businessConnections = new ArrayList<>();

        String userId = dataManager.getSharedPrefs().getUserId();
        List<UserConnections> adjustedConnections = new ArrayList<>();
        binding.swipeContainer.setRefreshing(false);

        if (dataManager.getSharedPrefs().isBusinessAccount()) {

        } else {

            UserProfile profile = viewModel.getUserProfile();

            String name = profile.getFirstName() + " " + profile.getLastName();
            binding.tvUserCode.setText(profile.getCode());
            binding.tvUserName.setText(name);

            Glide.with(getBaseActivity())
                    .load(profile.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(binding.ivUserProfileImage);

        }

        for (UserConnections connections : viewModel.getUserConnectionsList()) {

            if (connections.isOrgBus) {

                for (BusinessProfile profile : viewModel.getBusinessProfileList()) {

                    if (connections.requestingUserID.equals(userId)
                            && connections.consentingUserID.equals(profile.getId())) {

                        connections.setBusinessProfile(profile);
                        businessConnections.add(connections);

                    } else if (connections.getConsentingUserID().equals(userId)
                            && connections.getRequestingUserID().equals(profile.getId())) {

                        connections.setBusinessProfile(profile);
                        businessConnections.add(connections);
                    }
                }

            } else {

                for (UserProfile profile : viewModel.getUserProfileList()) {

                    if (connections.requestingUserID.equals(userId)
                            && connections.consentingUserID.equals(profile.getId())) {

                        connections.setUserProfile(profile);
                        individualConnections.add(connections);

                    } else if (connections.getConsentingUserID().equals(userId)
                            && connections.getRequestingUserID().equals(profile.getId())) {

                        connections.setUserProfile(profile);
                        individualConnections.add(connections);
                    }
                }

            }


        }


        Collections.sort(individualConnections, (o1, o2) -> o1.getUserProfile().getLastName().compareTo(o2.getUserProfile().getLastName()));
        Collections.sort(businessConnections, (o1, o2) -> Boolean.compare(o1.isIsOrgBus(), o2.isOrgBus));

        adjustedConnections.addAll(individualConnections);
        adjustedConnections.addAll(businessConnections);

        userConnectionsList.clear();
        userConnectionsList.addAll(adjustedConnections);
        contactListAdapter.notifyDataSetChanged();

        hideProgressSpinner();
    }

    @Override
    public void handleError(String errorMsg) {

        binding.swipeContainer.setRefreshing(false);
        hideProgressSpinner();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void setUpToolBar() {

        parentNavigator.showToolBar();
        parentActivity.hideCrmIcon();
        parentActivity.showContactsIcon();
        parentActivity.hideInfoIcon();
        parentActivity.hideNotificationsIcon();
    }

    private void setUpSearchView() {

        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setFocusable(false);


        //set the color for our search view edit text and text hint
        EditText searchEditText = binding.searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(getResources().getColor(R.color.material_icons_light));
        searchEditText.setHint("Search...");

        //set the color for our search view icon
        ImageView searchMagIcon = binding.searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setColorFilter(ContextCompat.getColor(getBaseActivity(), R.color.white));
        searchMagIcon.setVisibility(View.GONE);

        //set the line color
        View v = binding.searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);
    }

}
