package com.team.mamba.atlas.userInterface.dashBoard.crm.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.data.model.local.CrmFilter;
import com.team.mamba.atlas.databinding.CrmLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import com.team.mamba.atlas.userInterface.dashBoard.crm.edit_add_note.EditAddNotePageOneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.filter_list.CrmFilterSettingsFragment;
import com.team.mamba.atlas.userInterface.dashBoard.crm.selected_crm.SelectedCrmFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.ChangeFragments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class CrmFragment extends BaseFragment<CrmLayoutBinding, CrmViewModel>
        implements CrmNavigator, SearchView.OnQueryTextListener {


    @Inject
    CrmViewModel viewModel;

    @Inject
    CrmDataModel dataModel;

    private CrmLayoutBinding binding;
    private List<CrmNotes> crmNotesList = new ArrayList<>();
    private CrmAdapter crmAdapter;
    private DashBoardActivityNavigator parentNavigator;
    private DashBoardActivity parentActivity;
    private static final int SEND_CSV = 0;
    private List<CrmNotes> permCrmNotesList = new ArrayList<>();


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.crm_layout;
    }

    @Override
    public CrmViewModel getViewModel() {
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

        crmAdapter = new CrmAdapter(getViewModel(), crmNotesList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        setUpSearchView();

        binding.swipeContainer.setOnRefreshListener(() -> {

            viewModel.requestAllOpportunities(getViewModel());

        });

        setUpListeners();

        if (viewModel.getCrmNotesList().isEmpty()) {

            viewModel.requestAllOpportunities(getViewModel());

        } else {

            onCrmDataReturned();

            viewModel.requestAllOpportunities(getViewModel());

        }

        return binding.getRoot();
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

    private void setUpToolBar() {

        parentNavigator.showToolBar();
        parentActivity.showCrmIcon();
        parentActivity.hideContactsIcon();
        parentActivity.hideInfoIcon();
        parentActivity.hideNotificationsIcon();
    }

    @Override
    public void handleError(String error) {

        binding.swipeContainer.setRefreshing(false);
    }

    @Override
    public void onCrmDataReturned() {

        crmNotesList.clear();
        permCrmNotesList.clear();

        crmNotesList.addAll(getFilteredNotes());
        permCrmNotesList.addAll(getFilteredNotes());

        Collections
                .sort(crmNotesList, (o1, o2) -> Double.compare(o2.getAdjustedTimeStamp(), o1.getAdjustedTimeStamp()));

        binding.recyclerView.setAdapter(crmAdapter);

        crmAdapter.notifyDataSetChanged();
        binding.swipeContainer.setRefreshing(false);
    }

    @Override
    public List<CrmNotes> getPerCrmNotesList() {
        return permCrmNotesList;
    }

    @Override
    public void onRowClicked(CrmNotes notes) {

        ChangeFragments.replaceFragmentVertically(SelectedCrmFragment.newInstance(notes),
                getBaseActivity().getSupportFragmentManager(), "SelectedCrm", null);
        parentNavigator.hideToolBar();
    }


    @Override
    public void onInfoClicked() {

        showInfoDialog();
    }

    @Override
    public void onSettingsClicked() {

        parentNavigator.openSettingsScreen();
    }

    @Override
    public void onShareClicked() {

        showExportDialog();
        parentNavigator.hideToolBar();
    }

    @Override
    public void onCreateNewNoteClicked() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db.collection(AppConstants.USERS_COLLECTION).document();
        String noteId = newUserRef.getId();
        Long timeStamp = System.currentTimeMillis() / 1000;

        CrmNotes crmNotes = new CrmNotes();
        crmNotes.setId(noteId);
        crmNotes.setAuthorID(dataManager.getSharedPrefs().getUserId());
        crmNotes.setTimestamp(timeStamp);

        ChangeFragments.replaceFragmentVertically(EditAddNotePageOneFragment.newInstance(crmNotes, true),
                getBaseActivity().getSupportFragmentManager(), "PageOne", null);
        parentNavigator.hideToolBar();
    }

    @Override
    public void onFilterClicked() {

        ChangeFragments.replaceFragmentVertically(new CrmFilterSettingsFragment(),
                getBaseActivity().getSupportFragmentManager(), "FilterCrm", null);
        parentNavigator.hideToolBar();
    }

    @Override
    public boolean isInfoDialogShown() {
        return binding.dialogCrmInfo.getVisibility() == View.VISIBLE;
    }

    @Override
    public void hideCrmInfoDialog() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogCrmInfo.setVisibility(View.GONE))
                .playOn(binding.dialogCrmInfo);
    }


    private void showInfoDialog() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogCrmInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogCrmInfo);
    }

    @Override
    public boolean isExportScreenShown() {
        return binding.dialogExport.layoutExport.getVisibility() == View.VISIBLE;
    }

    @Override
    public void hideExportScreen() {

        YoYo.with(Techniques.SlideOutDown)
                .duration(500)
                .onEnd(animator -> binding.dialogExport.layoutExport.setVisibility(View.GONE))
                .playOn(binding.dialogExport.layoutExport);
    }

    private void showExportDialog() {

        YoYo.with(Techniques.SlideInUp)
                .duration(500)
                .onStart(animator -> binding.dialogExport.layoutExport.setVisibility(View.VISIBLE))
                .playOn(binding.dialogExport.layoutExport);

        //todo wait one second and show the recyclerview
    }


    @Override
    public void onExportButtonClicked() {

        if (isExternalStoragePermissionsGranted()) {

            hideExportScreen();

            if (binding.dialogExport.chkBoxContacts.isChecked()) {

                exportContacts();

            } else {

                exportOpportunities();
            }
        }
    }

    private void setUpListeners() {

        binding.dialogExport.chkBoxContacts.setOnClickListener(view -> {

            if (binding.dialogExport.chkBoxContacts.isChecked()) {

                binding.dialogExport.chkBoxOpportunities.setChecked(false);

            } else {

                binding.dialogExport.chkBoxOpportunities.setChecked(false);
                binding.dialogExport.chkBoxContacts.setChecked(true);
            }
        });

        binding.dialogExport.chkBoxOpportunities.setOnClickListener(view -> {

            if (binding.dialogExport.chkBoxOpportunities.isChecked()) {

                binding.dialogExport.chkBoxContacts.setChecked(false);

            } else {

                binding.dialogExport.chkBoxOpportunities.setChecked(true);
                binding.dialogExport.chkBoxContacts.setChecked(false);
            }
        });

        binding.dialogExport.layoutContacts.setOnClickListener(v -> {

            if (binding.dialogExport.chkBoxContacts.isChecked()) {

                binding.dialogExport.chkBoxOpportunities.setChecked(false);

            } else {

                binding.dialogExport.chkBoxOpportunities.setChecked(false);
                binding.dialogExport.chkBoxContacts.setChecked(true);
            }
        });

        binding.dialogExport.layoutOpportunities.setOnClickListener(v -> {

            if (binding.dialogExport.chkBoxOpportunities.isChecked()) {

                binding.dialogExport.chkBoxContacts.setChecked(false);

            } else {

                binding.dialogExport.chkBoxOpportunities.setChecked(true);
                binding.dialogExport.chkBoxContacts.setChecked(false);
            }
        });

    }


    private void writeCsvHeader(FileWriter writer, String h1) throws IOException {

        writer.write(h1);
    }

    private void writeCsvData(FileWriter writer, String data) throws IOException {

        writer.write(data);
    }


    /**
     * Takes all of the User's Contacts and exports them as a CSV file
     */
    private void exportContacts() {

        File root = Environment.getExternalStorageDirectory();
        File csvFile = new File(root, "CRM_from_Atlas.csv");
        Uri uri;

        FileWriter writer;

        try {

            writer = new FileWriter(csvFile);
            writeCsvHeader(writer, "First Name,");
            writeCsvHeader(writer, "Last Name,");
            writeCsvHeader(writer, "Email Name,");
            writeCsvHeader(writer, "Work Phone,");
            writeCsvHeader(writer, "Current Employer,");
            writeCsvHeader(writer, "Current Position,");
            writeCsvHeader(writer, "Work Street,");
            writeCsvHeader(writer, "Work City/State/Zip\n");

            for (UserProfile profile : viewModel.getUsersContactProfiles()) {

                writeCsvData(writer, profile.getFirstName().replace(",", " ") + ",");
                writeCsvData(writer, profile.getLastName().replace(",", " ") + ",");
                writeCsvData(writer, profile.getEmail().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkPhone().replace(",", " ") + ",");
                writeCsvData(writer, profile.getCurrentEmployer().replace(",", " ") + ",");
                writeCsvData(writer, profile.getCurrentPosition().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkStreet().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkCityStateZip() + "\n");

            }

            writer.flush();
            writer.close();

            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/html");

            if (sdk >= Build.VERSION_CODES.N) {

                uri = FileProvider.getUriForFile(getBaseActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        csvFile);
            } else {
                uri = Uri.fromFile(csvFile);
            }

            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(Intent.createChooser(emailIntent, "Pick an Email provider"), SEND_CSV);


        } catch (Exception e) {

            Logger.e(e.getMessage());
        }

    }

    /**
     * Takes all of the User's opportunities and exports them as a CSV file
     */
    private void exportOpportunities() {

        File root = Environment.getExternalStorageDirectory();
        File csvFile = new File(root, "CRM_from_Atlas.csv");
        Uri uri;
        UserProfile profile = viewModel.getUserProfile();

        FileWriter writer;

        try {

            writer = new FileWriter(csvFile);
            writeCsvHeader(writer, "Point of Contact,");
            writeCsvHeader(writer, "Opportunity Name,");
            writeCsvHeader(writer, "Where Met City/State,");
            writeCsvHeader(writer, "Where Met Event Name,");
            writeCsvHeader(writer, "How Met,");
            writeCsvHeader(writer, "Stage,");
            writeCsvHeader(writer, "Type,");

            writeCsvHeader(writer, "Opportunity Goal,");
            writeCsvHeader(writer, "Description,");
            writeCsvHeader(writer, "Next Step,");
            writeCsvHeader(writer, "Date Created,");
            writeCsvHeader(writer, "Date Closed,");

            writeCsvHeader(writer, "First Name,");
            writeCsvHeader(writer, "Last Name,");
            writeCsvHeader(writer, "Email,");
            writeCsvHeader(writer, "Work Phone,");
            writeCsvHeader(writer, "Current Employer,");
            writeCsvHeader(writer, "Current Position,");
            writeCsvHeader(writer, "Work Street,");
            writeCsvHeader(writer, "Work City/State/Zip\n");

            for (CrmNotes notes : viewModel.getCrmNotesList()) {

                writeCsvData(writer, notes.getPoc() + ",");
                writeCsvData(writer, notes.getNoteName() + ",");
                writeCsvData(writer, notes.getWhereMetCitySt() + ",");
                writeCsvData(writer, notes.getWhereMetEventName() + ",");
                writeCsvData(writer, notes.getHowMetToString() + ",");
                writeCsvData(writer, notes.getStageToString() + ",");
                writeCsvData(writer, notes.getTypeToString() + ",");
                writeCsvData(writer, notes.getOpportunityGoalToString() + ",");
                writeCsvData(writer, notes.getDesc() + ",");
                writeCsvData(writer, notes.getNextStepToString() + ",");
                writeCsvData(writer, notes.getDateCreatedToString() + ",");
                writeCsvData(writer, notes.getDateClosedToString() + ",");

                //Fixme the second iteration puts the first name in the wrong place
                writeCsvData(writer, profile.getFirstName().replace(",", " ") + ",");
                writeCsvData(writer, profile.getLastName().replace(",", " ") + ",");
                writeCsvData(writer, profile.getEmail().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkPhone().replace(",", " ") + ",");
                writeCsvData(writer, profile.getCurrentEmployer().replace(",", " ") + ",");
                writeCsvData(writer, profile.getCurrentPosition().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkStreet().replace(",", " ") + ",");
                writeCsvData(writer, profile.getWorkCityStateZip() + "\n");

            }

            writer.flush();
            writer.close();

            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/html");

            if (sdk >= Build.VERSION_CODES.N) {

                uri = FileProvider.getUriForFile(getBaseActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        csvFile);
            } else {
                uri = Uri.fromFile(csvFile);
            }

            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(Intent.createChooser(emailIntent, "Pick an Email provider"), SEND_CSV);


        } catch (Exception e) {

            Logger.e(e.getMessage());
        }

    }


    public boolean isExternalStoragePermissionsGranted() {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        //request specific permission from user
                        AppConstants.REQUEST_EXTERNAL_STORAGE_PERMISSIONS);

                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AppConstants.REQUEST_EXTERNAL_STORAGE_PERMISSIONS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            onExportButtonClicked();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int code = requestCode;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        crmAdapter.filter(newText);
        return true;
    }


    /**
     * This takes the list and filters it by the params
     * defined in {@link CrmFilterSettingsFragment}
     *
     * @return returns the filtered list
     */
    private List<CrmNotes> getFilteredNotes() {

        List<CrmNotes> filteredList = new ArrayList<>();

        if (parentNavigator.getCrmFilter() == null) {

            return viewModel.getCrmNotesList();

        } else {

            //filter by status
            for (CrmNotes notes : viewModel.getCrmNotesList()) {

                CrmFilter crmFilter = new CrmFilter(parentNavigator.getCrmFilter());
                // first give a valid value to each field
                if (crmFilter.getNextStep() < 0) {

                    crmFilter.setNextStep(notes.getNextStep());
                }

                if (crmFilter.getStatus() < 0) {

                    crmFilter.setStatus(notes.getStage());
                }

                if (crmFilter.getOpportunity() < 0) {

                    crmFilter.setOpportunity(notes.getOppGoal());
                }

                //second filter the list by all set values
                if (crmFilter.getOpportunity() == notes.getOppGoal()
                        && crmFilter.getStatus() == notes.getStage()
                        && crmFilter.getNextStep() == notes.getNextStep()) {

                    filteredList.add(notes);

                }

            }

            //third return our list
            return filteredList;

        }

    }

}
