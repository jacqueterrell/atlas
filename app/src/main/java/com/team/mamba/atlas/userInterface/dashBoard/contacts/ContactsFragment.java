package com.team.mamba.atlas.userInterface.dashBoard.contacts;

import android.Manifest;
import android.Manifest.permission;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Intents;
import android.provider.ContactsContract.Intents.Insert;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import com.team.mamba.atlas.utils.AppConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private static List<UserConnections> permUserConnectionsList = new ArrayList<>();
    private static List<UserConnections> filteredUserConnectionsList = new ArrayList<>();

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

        if (viewModel.getUserConnectionsList().isEmpty()) {

            showProgressSpinner();
            viewModel.requestContactsInfo(getViewModel());

        } else {

            if (!viewModel.getSavedUserId().equals(dataManager.getSharedPrefs().getUserId())) {

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

        //todo find out which values Matt wants me to sync ... code example below
        //syncAllContactRequest();
    }

    @Override
    public void onSettingsClicked() {

        parentActivity.openSettingsScreen();
    }

    @Override
    public void onProfileImageClicked() {

        if (binding.layoutIndividualProfile.getVisibility() == View.VISIBLE) {//showing all contacts

            parentActivity.openUserProfile(viewModel.getUserProfile());

        } else { //showing business directory

            if (viewModel.getSelectedBusinessProfile() != null) {

                parentActivity.openBusinessProfile(viewModel.getSelectedBusinessProfile());

            }
        }
    }

    @Override
    public void onBusinessFilterClicked() {

        binding.ivGroupFilter.setVisibility(View.GONE);
        binding.ivIndividualFilter.setVisibility(View.VISIBLE);

        binding.layoutIndividualProfile.setVisibility(View.GONE);
        binding.ivBusinessProfile.setVisibility(View.VISIBLE);

        setBusinessContactProfiles();
        createCharList();
    }

    @Override
    public void onIndividualFilterClicked() {

        binding.ivGroupFilter.setVisibility(View.VISIBLE);
        binding.ivIndividualFilter.setVisibility(View.GONE);

        binding.ivBusinessProfile.setVisibility(View.GONE);
        binding.layoutIndividualProfile.setVisibility(View.VISIBLE);

        userConnectionsList.clear();
        filteredUserConnectionsList.clear();
        filteredUserConnectionsList.addAll(permUserConnectionsList);
        userConnectionsList.addAll(permUserConnectionsList);
        contactListAdapter.notifyDataSetChanged();
        createCharList();
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
                    }
                }

            } else {

                for (UserProfile profile : viewModel.getUserProfileList()) {

                    if (connections.requestingUserID.equals(userId)
                            && connections.consentingUserID.equals(profile.getId())) {

                        connections.setUserProfile(profile);
                        individualConnections.add(connections);

                    }
                }

            }

        }

        Collections.sort(individualConnections,
                (o1, o2) -> o1.getUserProfile().getLastName().compareTo(o2.getUserProfile().getLastName()));
        Collections.sort(businessConnections, (o1, o2) -> Boolean.compare(o1.isIsOrgBus(), o2.isOrgBus));

        adjustedConnections.addAll(individualConnections);
        adjustedConnections.addAll(businessConnections);

        userConnectionsList.clear();
        permUserConnectionsList.clear();
        filteredUserConnectionsList.clear();

        permUserConnectionsList.addAll(adjustedConnections);
        userConnectionsList.addAll(adjustedConnections);
        filteredUserConnectionsList.addAll(adjustedConnections);

        contactListAdapter.notifyDataSetChanged();

        createCharList();
        hideProgressSpinner();
    }


    private void setBusinessContactProfiles() {

        List<String> businessContactList = new ArrayList<>();
        List<UserConnections> userProfileConnections = new ArrayList<>();
        List<UserConnections> businessAssociatesList = new ArrayList<>();

        //gets a list of all business contacts
        for (UserConnections connections : userConnectionsList) {

            if (connections.isOrgBus) {

                viewModel.setSelectedBusinessProfile(connections.getBusinessProfile());

                for (Map.Entry<String, String> entry : connections.getBusinessProfile().contacts.entrySet()) {

                    String userId = entry.getKey();

                    if (!businessContactList.contains(userId)) {

                        businessContactList.add(userId);
                    }
                }
            } else {

                userProfileConnections.add(connections);
            }
        }

        //Gets all contacts connected to the user's business account
        for (UserConnections connections : viewModel.getUserConnectionsList()) {

            if (!connections.isOrgBus) {

                if (businessContactList.contains(connections.getUserProfile().getId())) {

                    businessAssociatesList.add(connections);
                }
            }
        }

        //add the user's profile to the company directory?
        UserConnections userConnections = new UserConnections();
        userConnections.setUserProfile(viewModel.getUserProfile());
        businessAssociatesList.add(userConnections);

        Collections.sort(businessAssociatesList,
                (o1, o2) -> o1.getUserProfile().getLastName().compareTo(o2.getUserProfile().getLastName()));

        userConnectionsList.clear();
        filteredUserConnectionsList.clear();

        userConnectionsList.addAll(businessAssociatesList);
        filteredUserConnectionsList.addAll(businessAssociatesList);

        contactListAdapter.notifyDataSetChanged();

        hideProgressSpinner();
    }

    /**
     * Populates our floating textView
     */
    private void createCharList() {

        StringBuilder stringBuilder = new StringBuilder();
        List<String> charList = new ArrayList<>();
        List<UserConnections> businessConnections = new ArrayList<>();

        for (UserConnections connections : userConnectionsList) {

            if (connections.isOrgBus) {

                businessConnections.add(connections);

            } else {

                String firstChar = connections.getUserProfile().getLastName().substring(0, 1);

                if (!charList.contains(firstChar)) {

                    stringBuilder.append(firstChar + "\n");
                    charList.add(firstChar);
                }
            }
        }

        if (!businessConnections.isEmpty()) {

            stringBuilder.append("+");
        }

        binding.tvCharList.setText(stringBuilder.toString());
    }

    @Override
    public void handleError(String errorMsg) {

        binding.swipeContainer.setRefreshing(false);
        hideProgressSpinner();
    }

    @Override
    public List<UserConnections> getPermConnectionList() {
        return filteredUserConnectionsList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        contactListAdapter.filter(newText);
        return true;
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

    private void syncAllContactRequest(){

        String DisplayName = "XYZ";
        String MobileNumber = "123456";
        String HomeNumber = "1111";
        String WorkNumber = "2222";
        String emailID = "email@nomail.com";
        String company = "bad";
        String jobTitle = "abcd";

        ArrayList < ContentProviderOperation > ops = new ArrayList < > ();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
        if (HomeNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        //------------------------------------------------------ Work Numbers
        if (WorkNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (emailID != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!company.equals("") && !jobTitle.equals("")) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        // Asking the Contact provider to create a new contact

        if (isWriteContactsPermissionsGranted()){

            try {
                getBaseActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
                showToastLong("Error sending contacts " + e.getLocalizedMessage());
            }
        }

    }


    public boolean isWriteContactsPermissionsGranted() {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{Manifest.permission.WRITE_CONTACTS},   //request specific permission from user
                        AppConstants.REQUEST_WRITE_CONTACTS_PERMISSIONS);

                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AppConstants.REQUEST_WRITE_CONTACTS_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            onSyncContactsClicked();
        }
    }


}
