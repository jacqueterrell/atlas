package com.team.mamba.atlas.userInterface.dashBoard.Crm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.CrmNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.CrmLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.utils.AppConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CrmFragment extends BaseFragment<CrmLayoutBinding,CrmViewModel>
        implements CrmNavigator {


    @Inject
    CrmViewModel viewModel;

    @Inject
    CrmDataModel dataModel;

    private CrmLayoutBinding binding;
    private List<CrmNotes> crmNotesList = new ArrayList<>();
    private CrmAdapter crmAdapter;
    private DashBoardActivityNavigator parentNavigator;
    private static final int SEND_CSV = 0;


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

         crmAdapter = new CrmAdapter(getViewModel(),crmNotesList);
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
         binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
         binding.recyclerView.setAdapter(crmAdapter);

         setUpListeners();

         if (viewModel.getCrmNotesList().isEmpty()){

             viewModel.requestAllOpportunities(getViewModel());

         } else {

             onCrmDataReturned();
         }

         return binding.getRoot();
    }

    @Override
    public void onCrmDataReturned() {

        crmNotesList.clear();
        crmNotesList.addAll(viewModel.getCrmNotesList());
        crmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRowClicked(CrmNotes notes) {

        //todo open crm details
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
    }

    @Override
    public void onCreateNewNoteClicked() {

    }

    @Override
    public void onFilterClicked() {

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



    private void showInfoDialog(){

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

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogExport.layoutExport.setVisibility(View.GONE))
                .playOn(binding.dialogExport.layoutExport);
    }

    private void showExportDialog(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogExport.layoutExport.setVisibility(View.VISIBLE))
                .playOn(binding.dialogExport.layoutExport);
    }

    @Override
    public void onExportButtonClicked() {


        if (isExternalStoragePermissionsGranted()){

            hideExportScreen();

            if (binding.dialogExport.chkBoxContacts.isChecked()){

                exportContacts();

            } else {

                exportOpportunities();
            }
        }
    }

    private void setUpListeners(){

        binding.dialogExport.chkBoxContacts.setOnCheckedChangeListener((buttonView, isChecked) -> {


            if (isChecked){

                binding.dialogExport.chkBoxOpportunities.setChecked(false);

            }
        });

        binding.dialogExport.chkBoxOpportunities.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){

                binding.dialogExport.chkBoxContacts.setChecked(false);

            }
        });

        binding.dialogExport.layoutContacts.setOnClickListener(v -> binding.dialogExport.chkBoxContacts.setChecked(true));

        binding.dialogExport.layoutOpportunities.setOnClickListener(v -> binding.dialogExport.chkBoxOpportunities.setChecked(true));

    }


    private void writeCsvHeader(FileWriter writer, String h1) throws IOException {

        writer.write(h1);
    }

    private void writeCsvData(FileWriter writer, String data) throws IOException {

        writer.write(data);
    }


    private void exportContacts(){

        File root = Environment.getExternalStorageDirectory();
        File csvFile = new File(root, "CRM_from_Atlas.csv");
        Uri uri;

        FileWriter writer;

        try {

            writer = new FileWriter(csvFile);
            writeCsvHeader(writer,"First Name,");
            writeCsvHeader(writer,"Last Name,");
            writeCsvHeader(writer,"Email Name,");
            writeCsvHeader(writer,"Work Phone,");
            writeCsvHeader(writer,"Current Employer,");
            writeCsvHeader(writer,"Current Position,");
            writeCsvHeader(writer,"Work Street,");
            writeCsvHeader(writer,"Work City/State/Zip\n");

            for (UserProfile profile : viewModel.getUsersContactProfiles()){

                writeCsvData(writer,profile.getFirstName() + ",");
                writeCsvData(writer,profile.getLastName() + ",");
                writeCsvData(writer,profile.getEmail() + ",");
                writeCsvData(writer,profile.getWorkPhone() + ",");
                writeCsvData(writer,profile.getCurrentEmployer() + ",");
                writeCsvData(writer,profile.getCurrentPosition() + ",");
                writeCsvData(writer,profile.getWorkStreet() + ",");
                writeCsvData(writer,profile.getWorkCityStateZip() + "\n");

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


        } catch (Exception e){

            Logger.e(e.getMessage());
        }

    }

    private void exportOpportunities(){

        File root = Environment.getExternalStorageDirectory();
        File csvFile = new File(root, "CRM_from_Atlas.csv");
        Uri uri;

        FileWriter writer;

        try {

            writer = new FileWriter(csvFile);
            writeCsvHeader(writer,"First Name");
            writeCsvHeader(writer,"Last Name");
            writeCsvHeader(writer,"Email Name");
            writeCsvHeader(writer,"Work Phone");
            writeCsvHeader(writer,"Current Employer");
            writeCsvHeader(writer,"Current Position");
            writeCsvHeader(writer,"Work Street");
            writeCsvHeader(writer,"Work City/State/Zip\n");

            for (UserProfile profile : viewModel.getUsersContactProfiles()){

                writeCsvData(writer,profile.getFirstName());
                writeCsvData(writer,profile.getLastName());
                writeCsvData(writer,profile.getEmail());
                writeCsvData(writer,profile.getWorkPhone());
                writeCsvData(writer,profile.getCurrentEmployer());
                writeCsvData(writer,profile.getCurrentPosition());
                writeCsvData(writer,profile.getWorkStreet());
                writeCsvData(writer,profile.getWorkCityStateZip() + "\n");

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


        } catch (Exception e){

            Logger.e(e.getMessage());
        }

    }


    public boolean isExternalStoragePermissionsGranted() {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},   //request specific permission from user
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AppConstants.REQUEST_EXTERNAL_STORAGE_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            onExportButtonClicked();

        }
    }


}
