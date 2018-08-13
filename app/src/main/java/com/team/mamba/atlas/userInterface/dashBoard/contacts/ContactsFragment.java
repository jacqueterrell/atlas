package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.data.model.api.UserConnections;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.ContactsLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactsFragment extends BaseFragment<ContactsLayoutBinding,ContactsViewModel> implements ContactsNavigator {


    @Inject
    ContactsViewModel viewModel;

    @Inject
    ContactsDataModel dataModel;

    private ContactsLayoutBinding binding;
    private DashBoardActivityNavigator parentNavigator;
    private DashBoardActivity parentActivity;
    private List<UserConnections> userConnectionsList = new ArrayList<>();



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
        return null;
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



         setUpToolBar();
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

            for (BusinessProfile profile : viewModel.getBusinessProfileList()) {

                if (profile.getId().equals(userConnections.getConsentingUserID())) {

                    parentNavigator.openBusinessProfile(profile);

                }
            }

        } else {

            for (UserProfile profile : viewModel.getUserProfileList()){

                if (profile.getId().equals(userConnections.getConsentingUserID())){

                    parentNavigator.openUserProfile(profile);

                }
            }
        }

    }

    @Override
    public void handleError(String errorMsg) {

        hideProgressSpinner();
    }

    private void setUpToolBar() {

        parentNavigator.showToolBar();
        parentActivity.hideCrmIcon();
        parentActivity.showContactsIcon();
        parentActivity.hideInfoIcon();
        parentActivity.hideNotificationsIcon();
    }
}
