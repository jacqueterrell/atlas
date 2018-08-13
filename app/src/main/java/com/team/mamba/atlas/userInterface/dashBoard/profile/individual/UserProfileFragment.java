package com.team.mamba.atlas.userInterface.dashBoard.profile.individual;

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

            if (binding.contactProfile.tvPosition.getText().toString().trim().equals(",")){

                binding.contactProfile.tvPosition.setText("");
            }

        }

         return binding.getRoot();
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

//        ChangeFragments.addFragmentFadeIn(new EditWorkFragment(),getBaseActivity()
//                .getSupportFragmentManager(),"EditWorkHistory",null);
    }

    @Override
    public void editEductionInfo() {

        ChangeFragments.addFragmentFadeIn(EditEducationFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EditAddress",null);
    }



    @Override
    public void onUserProfileClicked() {

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

}
