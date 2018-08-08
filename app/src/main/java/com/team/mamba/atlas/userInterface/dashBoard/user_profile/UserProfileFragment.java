package com.team.mamba.atlas.userInterface.dashBoard.user_profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.UserProfile;
import com.team.mamba.atlas.databinding.UserProfileLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;

import java.util.List;
import java.util.Map;

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


         setUserDetails();
         return binding.getRoot();
    }

    @Override
    public void onSettingsClicked() {

        parentNavigator.openSettingsScreen();
    }

    @Override
    public void setUserDetails() {

        if (profile.getId().equals(dataManager.getSharedPrefs().getUserId())){

            showSnackbar("Is the User");

        } else {

            showSnackbar("Is a contact");
            showNonEditableScreen();

        }


        StringBuilder workHistoryBuilder = new StringBuilder();
        StringBuilder educationBuilder = new StringBuilder();


        String name = profile.getFirstName() + " " + profile.getLastName();
        binding.tvName.setText(name);
        binding.tvUserCode.setText(profile.getCode());
        binding.tvCellPhone.setText(profile.getPhone());
        binding.tvOfficePhone.setText(profile.getWorkPhone());
        binding.tvHomeAddress.setText(profile.getCityStateZip());
        binding.tvPersonalPhone.setText(profile.getPersonalPhone());
        binding.tvFaxPhone.setText(profile.getFax());
        binding.tvPersonalEmail.setText(profile.getEmail());
        binding.tvWorkEmail.setText(profile.getWorkEmail());
        binding.tvWorkAddress.setText(profile.getWorkCityStateZip());

        if (profile.getImageUrl() != null){

            Glide.with(getBaseActivity())
                    .load(profile.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(binding.ivUserProfile);
        }



        for (Map<String, String> map : profile.getWorkHistory()) {

            for (Map.Entry<String,String> entry : map.entrySet()){

                String key = entry.getKey();
                String value = entry.getValue();

                workHistoryBuilder.append(value)
                        .append("\n");
            }


        }

        for (Map<String, String> map : profile.getEducation()) {

            for (Map.Entry<String,String> entry : map.entrySet()){

                String key = entry.getKey();
                String value = entry.getValue();

                educationBuilder.append(value)
                        .append("\n");
            }


        }

        binding.tvWorkHistory.setText(workHistoryBuilder.toString());
        binding.tvEducation.setText(educationBuilder.toString());

    }

    private void showNonEditableScreen(){


    }


    @Override
    public void onUserProfileClicked() {

    }
}
