package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.databinding.UserProfileForContactBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.formatData.RegEx;

import javax.inject.Inject;

public class ContactProfileFragment extends BaseFragment<UserProfileForContactBinding,ContactProfileViewModel>
implements ContactProfileNavigator{


    @Inject
    ContactProfileViewModel viewModel;

    @Inject
    ContactProfileDataModel dataModel;

    private UserProfileForContactBinding binding;
    private DashBoardActivityNavigator parentNavigator;
    private static UserProfile profile;
    private static final String CELL_PHONE = "cellPhone";
    private static final String OFFICE_PHONE = "officePhone";
    private static final String HOME_PHONE = "homePhone";
    private static final String PERSONAL_PHONE = "personalPhone";
    private static int connectionType;

    public static ContactProfileFragment newInstance(UserProfile userProfile){

        profile = userProfile;
        return new ContactProfileFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_profile_for_contact;
    }

    @Override
    public ContactProfileViewModel getViewModel() {
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
         binding.setProfile(profile);

        if (profile.getImageUrl() != null){

            Glide.with(getBaseActivity())
                    .load(profile.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(binding.ivUserProfile);
        }

        viewModel.getConnectionType(getViewModel(),profile);

         return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setContactsDefaultValues();
    }

    @Override
    public void contactCellPhoneClicked() {

        viewModel.setSelectedPhone(CELL_PHONE);
        callPhone(profile.getPhone());
    }

    @Override
    public void contactOnOfficePhoneClicked() {

        viewModel.setSelectedPhone(OFFICE_PHONE);
        callPhone(profile.getWorkPhone());
    }

    @Override
    public void contactOnHomePhoneClicked() {

        viewModel.setSelectedPhone(HOME_PHONE);
        callPhone(profile.getHomePhone());
    }

    @Override
    public void contactOnPersonalPhoneClicked() {

        viewModel.setSelectedPhone(PERSONAL_PHONE);
        callPhone(profile.getPersonalPhone());
    }


    @Override
    public void contactOnFaxClicked() {

        sendToClipBoard(profile.getFax());
    }

    @Override
    public void contactOnPersonalEmailClicked() {

        sendToClipBoard(profile.getEmail());
    }

    @Override
    public void contactOnWorkEmailClicked() {

        sendToClipBoard(profile.getWorkEmail());
    }

    @Override
    public void contactOnHomeAddressClicked() {

        sendToClipBoard(profile.getCityStateZip());
    }

    @Override
    public void contactOnWorkAddressClicked() {

        sendToClipBoard(profile.getWorkCityStateZip());
    }

    @Override
    public void contactOnWorkHistoryClicked() {

        sendToClipBoard(profile.getWorkHistoryString());
    }

    @Override
    public void contactOnEducationClicked() {

        sendToClipBoard(profile.getEducationString());
    }

    private void callPhone(String profilePhone){


        String adjustedPhone = profilePhone.replaceAll(RegEx.REMOVE_NON_DIGITS,"");

        if (adjustedPhone.isEmpty()){

            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());

        builder.setMessage(profile.getPhone())
                .setPositiveButton("Call", (dialog, id) -> {

                    Long phone2 = Long.valueOf(adjustedPhone);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone2));//change the number


                    if (isPhonePermissionsGranted()) {

                        try {

                            startActivity(callIntent);
                            showToastLong("dialing " + profile.getPhone());

                        } catch (Exception e) {
                            Logger.e(e.getMessage());
                        }
                    }

                })

                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder.create();
        alert11.show();

    }


    @Override
    public void onConnectionTypeSaved() {

        if (viewModel.getConsentingProfile() == null){

            connectionType = 3;

        } else {

            viewModel.getConsentingProfile().setConnectionType(0);
            connectionType = viewModel.getConsentingProfile().getConnectionType();
        }

        if (connectionType == 0 || connectionType == 1){//family can see everything

            binding.layoutContactCellPhone.setVisibility(View.VISIBLE);
            binding.layoutContactOfficePhone.setVisibility(View.VISIBLE);
            binding.layoutContactHomePhone.setVisibility(View.VISIBLE);
            binding.layoutContactPersonalPhone.setVisibility(View.VISIBLE);
            binding.layoutContactFax.setVisibility(View.VISIBLE);
            binding.layoutContactPersonalEmail.setVisibility(View.VISIBLE);
            binding.layoutContactWorkEmail.setVisibility(View.VISIBLE);
            binding.layoutContactWorkHistory.setVisibility(View.VISIBLE);
            binding.layoutContactEducation.setVisibility(View.VISIBLE);
            binding.layoutContactHomeAddress.setVisibility(View.VISIBLE);
            binding.layoutContactWorkAddress.setVisibility(View.VISIBLE);

        } if (connectionType == 2){ //connection type is 2(New Acquaintance)

            binding.layoutContactCellPhone.setVisibility(View.VISIBLE);
            binding.layoutContactOfficePhone.setVisibility(View.VISIBLE);
            binding.layoutContactFax.setVisibility(View.VISIBLE);
            binding.layoutContactWorkEmail.setVisibility(View.VISIBLE);
            binding.layoutContactWorkAddress.setVisibility(View.VISIBLE);

            binding.layoutContactWorkHistory.setVisibility(View.GONE);
            binding.layoutContactEducation.setVisibility(View.GONE);
            binding.layoutContactHomePhone.setVisibility(View.GONE);
            binding.layoutContactHomeAddress.setVisibility(View.GONE);
            binding.layoutContactPersonalPhone.setVisibility(View.GONE);

        } else { //connectionType = Business

            binding.layoutContactCellPhone.setVisibility(View.VISIBLE);
            binding.layoutContactOfficePhone.setVisibility(View.VISIBLE);
            binding.layoutContactFax.setVisibility(View.VISIBLE);
            binding.layoutContactWorkEmail.setVisibility(View.VISIBLE);
            binding.layoutContactWorkAddress.setVisibility(View.VISIBLE);
            binding.layoutContactWorkHistory.setVisibility(View.VISIBLE);
            binding.layoutContactEducation.setVisibility(View.GONE);

            binding.layoutContactHomePhone.setVisibility(View.GONE);
            binding.layoutContactHomeAddress.setVisibility(View.GONE);
            binding.layoutContactPersonalPhone.setVisibility(View.GONE);
        }

        binding.layoutContactProfile.setVisibility(View.VISIBLE);
        setContactsDefaultValues();
    }


    public boolean isPhonePermissionsGranted() {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                        AppConstants.REQUEST_PHONE_PERMISSIONS);

                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }

    }


    private void setContactsDefaultValues(){

        if (binding.tvPosition.getText().toString().trim().equals(",")){

            binding.tvPosition.setText("");
        }

        //phone info
        if (binding.tvCellPhone.getText().toString().isEmpty()){

            binding.tvCellPhone.setText("...");
        }

        if (binding.tvOfficePhone.getText().toString().isEmpty()){

            binding.tvOfficePhone.setText("...");
        }

        if (binding.tvHomePhone.getText().toString().isEmpty()){

            binding.tvHomePhone.setText("...");
        }

        if (binding.tvFaxPhone.getText().toString().isEmpty()){

            binding.tvFaxPhone.setText("...");
        }

        if (binding.tvPersonalPhone.getText().toString().isEmpty()){

            binding.tvPersonalPhone.setText("...");
        }

        //email info
        if (binding.tvWorkEmail.getText().toString().isEmpty()){

            binding.tvWorkEmail.setText("...");
        }

        if (binding.tvPersonalEmail.getText().toString().isEmpty()){

            binding.tvPersonalEmail.setText("...");
        }

        //address info
        if (binding.tvHomeAddress.getText().toString().isEmpty()){

            binding.tvHomeAddress.setText("...");
        }

        if (binding.tvWorkAddress.getText().toString().isEmpty()){

            binding.tvWorkAddress.setText("...");
        }

        //work history info
        if (binding.tvWorkHistory.getText().toString().isEmpty()){

            binding.tvWorkHistory.setText("...");
        }

        //education info
        if (binding.tvEducation.getText().toString().isEmpty()){

            binding.tvEducation.setText("...");
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == AppConstants.REQUEST_PHONE_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (viewModel.getSelectedPhone().equals(CELL_PHONE)){

                contactCellPhoneClicked();

            } else if (viewModel.getSelectedPhone().equals(OFFICE_PHONE)){

                contactOnOfficePhoneClicked();

            } else if (viewModel.getSelectedPhone().equals(HOME_PHONE)){

                contactOnHomePhoneClicked();

            } else if (viewModel.getSelectedPhone().equals(PERSONAL_PHONE)){

                contactOnPersonalPhoneClicked();
            }

        }
    }


    /**
     * Copys the text to the clip board
     * @param text
     */
    private void sendToClipBoard(String text){

        String label = text + " is copied to your clipboard";

        if (!text.isEmpty() && !text.equals("...")){

            try {

                ClipboardManager clipboard = (ClipboardManager) getBaseActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(label, text);
                clipboard.setPrimaryClip(clip);
                showToastShort(label);

            } catch (Exception e){

                Logger.e(e.getMessage());
            }

            try{

                Vibrator v = (Vibrator) getBaseActivity().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(400);

            } catch (Exception e){

                Logger.e(e.getMessage());
            }
        }

    }



}
