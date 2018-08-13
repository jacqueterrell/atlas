package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.UserProfileLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;

import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_address_info.EditAddressFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_email_info.EditEmailFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_phone_info.EditPhoneFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_work_history.EditWorkFragment;
import com.team.mamba.atlas.utils.AppConstants;
import com.team.mamba.atlas.utils.ChangeFragments;
import com.team.mamba.atlas.utils.formatData.RegEx;

import java.io.File;
import javax.inject.Inject;

public class UserProfileFragment extends BaseFragment<UserProfileLayoutBinding,UserProfileViewModel>
        implements UserProfileNavigator {


    @Inject
    UserProfileViewModel viewModel;

    @Inject
    UserProfileDataModel dataModel;

    private UserProfileLayoutBinding binding;
    private DashBoardActivityNavigator parentNavigator;
    private static UserProfile profile;
    private static final String CELL_PHONE = "cellPhone";
    private static final String OFFICE_PHONE = "officePhone";
    private static final String HOME_PHONE = "homePhone";
    private static final String PERSONAL_PHONE = "personalPhone";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY = 2;
    private File imageFile;

    public static UserProfileFragment newInstance(UserProfile userProfile){

        profile = userProfile;
        return new UserProfileFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_profile_layout;
    }

    @Override
    public UserProfileViewModel getViewModel() {
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

         //check to see if we are viewing the user's profile or one of his contact's
        if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

            binding.setProfile(profile);
            binding.contactProfile.layoutContactProfile.setVisibility(View.GONE);

            if (profile.getImageUrl() != null){

                Glide.with(getBaseActivity())
                        .load(profile.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(binding.ivUserProfile);
            }

        } else {

            binding.contactProfile.layoutContactProfile.setVisibility(View.VISIBLE);
            binding.contactProfile.setProfile(profile);

            if (profile.getImageUrl() != null){

                Glide.with(getBaseActivity())
                        .load(profile.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(binding.contactProfile.ivUserProfile);
            }

        }

         return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        setUserDefaultValues();
        setContactsDefaultValues();
    }

    private void setUserDefaultValues(){


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

    private void setContactsDefaultValues(){

        //phone info
        if (binding.contactProfile.tvCellPhone.getText().toString().isEmpty()){

            binding.contactProfile.tvCellPhone.setText("...");
        }

        if (binding.contactProfile.tvOfficePhone.getText().toString().isEmpty()){

            binding.contactProfile.tvOfficePhone.setText("...");
        }

        if (binding.contactProfile.tvHomePhone.getText().toString().isEmpty()){

            binding.contactProfile.tvHomePhone.setText("...");
        }

        if (binding.contactProfile.tvFaxPhone.getText().toString().isEmpty()){

            binding.contactProfile.tvFaxPhone.setText("...");
        }

        if (binding.contactProfile.tvPersonalPhone.getText().toString().isEmpty()){

            binding.contactProfile.tvPersonalPhone.setText("...");
        }

        //email info
        if (binding.contactProfile.tvWorkEmail.getText().toString().isEmpty()){

            binding.contactProfile.tvWorkEmail.setText("...");
        }

        if (binding.contactProfile.tvPersonalEmail.getText().toString().isEmpty()){

            binding.contactProfile.tvPersonalEmail.setText("...");
        }

        //address info
        if (binding.contactProfile.tvHomeAddress.getText().toString().isEmpty()){

            binding.contactProfile.tvHomeAddress.setText("...");
        }

        if (binding.contactProfile.tvWorkAddress.getText().toString().isEmpty()){

            binding.contactProfile.tvWorkAddress.setText("...");
        }

        //work history info
        if (binding.contactProfile.tvWorkHistory.getText().toString().isEmpty()){

            binding.contactProfile.tvWorkHistory.setText("...");
        }

        //education info
        if (binding.contactProfile.tvEducation.getText().toString().isEmpty()){

            binding.contactProfile.tvEducation.setText("...");
        }


    }

    @Override
    public void onSettingsClicked() {

        parentNavigator.openSettingsScreen();
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

    @Override
    public void editPhoneInfo() {

        ChangeFragments.addFragmentFadeIn(EditPhoneFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditPhone",null);
    }

    @Override
    public void editEmailInfo() {

        ChangeFragments.addFragmentFadeIn(EditEmailFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditEmail",null);
    }

    @Override
    public void editAddressInfo() {

        ChangeFragments.addFragmentFadeIn(EditAddressFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditAddress",null);
    }

    @Override
    public void editWorkHistoryInfo() {

        ChangeFragments.addFragmentFadeIn(EditWorkFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditWorkHistory",null);
    }

    @Override
    public void editEductionInfo() {

        ChangeFragments.addFragmentFadeIn(EditEducationFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditAddress",null);
    }



    @Override
    public void onUserProfileClicked() {

        FragmentManager manager = getBaseActivity().getSupportFragmentManager();
        AddNewImageDialogFragment dialog = AddNewImageDialogFragment.newInstance(this);
        dialog.show(manager,"ImageDialog");
    }

    @Override
    public void openCamera() {

        if (isCameraPermissionsGranted()){

            try {
                takePicture();
            } catch (Exception e) {
                Logger.e("Error opening camera",e.getMessage());
            }
        }
    }

    @Override
    public void openGallery() {

        if (isReadStoragePermissionsGranted()) {

            openPhotoGallery();
        }
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

        } else if (requestCode == AppConstants.REQUEST_CAMERA_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            try {
                takePicture();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == AppConstants.REQUEST_READ_EXTERNAL_STORAGE) {

            openPhotoGallery();
        }
    }

    @Override
    public void onProfileUpdated() {

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

    @Override
    public String getImagePath() {

        Uri uri = getViewModel().getImageUri();

        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getBaseActivity().getContentResolver().query(uri, proj, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                int columnIndex = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(columnIndex);
            }

            cursor.close();
        }

        if (result == null) {
            result = "Not found";
        }

        return result;

    }

    /**
     * Use the camera to capture a picture and save it into a unique file
     */
    public void takePicture() throws Exception {

        File storageDir = getBaseActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageFileName = "atlas_profile";
        imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageUri;

        int nougat = Build.VERSION_CODES.N;
        if (sdk >= nougat) {
            mImageUri = FileProvider.getUriForFile(getBaseActivity(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        } else {
            mImageUri = Uri.fromFile(imageFile);
        }

        getViewModel().setSelfiePath(imageFile.getAbsolutePath());

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (takePictureIntent.resolveActivity(getBaseActivity().getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }


    /**
     * Opens the device photo gallery
     */
    public void openPhotoGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }


    public boolean isReadStoragePermissionsGranted() {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        AppConstants.REQUEST_READ_EXTERNAL_STORAGE);

                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }
    }

    public boolean isCameraPermissionsGranted() {


        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        AppConstants.REQUEST_CAMERA_PERMISSIONS);


                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_IMAGE_CAPTURE : {

                if (resultCode == Activity.RESULT_OK) {

                    getViewModel().uploadImage(getViewModel(),false);

                    Glide.with(getContext())
                            .load(getViewModel().getSelfiePath())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(binding.ivUserProfile);

                }

                    break;
            }
            case REQUEST_GALLERY :{

                if (resultCode == Activity.RESULT_OK) {

                    getViewModel().setImageUri(data.getData());

                    Glide.with(getContext())
                            .load(getImagePath())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(binding.ivUserProfile);

                    getViewModel().uploadImage(getViewModel(),true);

                }
                    break;
            }
        }
    }
}
