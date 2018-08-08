package com.team.mamba.atlas.userInterface.dashBoard.business_profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.BusinessProfile;
import com.team.mamba.atlas.databinding.BusinessProfileLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import javax.inject.Inject;

public class BusinessProfileFragment extends BaseFragment<BusinessProfileLayoutBinding, BusinessProfileViewModel>
implements BusinessProfileNavigator{


    @Inject
    BusinessProfileViewModel viewModel;

    @Inject
    BusinessProfileDataModel dataModel;

    private BusinessProfileLayoutBinding binding;


    public static BusinessProfileFragment newInstance(){

        return new BusinessProfileFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.business_profile_layout;
    }

    @Override
    public BusinessProfileViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
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

         viewModel.getBusinessDetails(getViewModel());

         return binding.getRoot();
    }

    @Override
    public void onProfileImageClicked() {

    }

    @Override
    public void setBusinessDetails() {

        BusinessProfile profile = viewModel.getBusinessProfile();

        binding.tvAddress.setText(profile.getCityStateZip());
        binding.tvCode.setText(profile.getCode());
        binding.tvContactName.setText(profile.getContactName());
        binding.tvEmail.setText(profile.getEmail());
        binding.tvPhone.setText(profile.getPhone());
        binding.tvFax.setText(profile.getFax());
    }
}
