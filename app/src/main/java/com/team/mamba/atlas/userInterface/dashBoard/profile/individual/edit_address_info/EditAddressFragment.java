package com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_address_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.EditAddressLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.individual.edit_education_info.EditEducationFragment;
import com.team.mamba.atlas.utils.ChangeFragments;

import javax.inject.Inject;

public class EditAddressFragment extends BaseFragment<EditAddressLayoutBinding,EditAddressViewModel> implements EditAddressNavigator {

    @Inject
    EditAddressViewModel viewModel;

    @Inject
    EditAddressDataModel dataModel;

    private EditAddressLayoutBinding binding;
    private static UserProfile profile;

    public static EditAddressFragment newInstance(UserProfile userProfile){

        profile = userProfile;
        return new EditAddressFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_address_layout;
    }

    @Override
    public EditAddressViewModel getViewModel() {
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


        if (!profile.getCityStateZip().trim().equals("...")){binding.etCityStateZip.setText(profile.getCityStateZip());}
        if (!profile.getStreet().trim().equals("...")){binding.etStreetAddress.setText(profile.getStreet());}
        if (!profile.getWorkCityStateZip().trim().equals("...")){binding.etWorkCityStateZip.setText(profile.getWorkCityStateZip());}
        if (!profile.getWorkStreet().trim().equals("...")){binding.etWorkStreetAddress.setText(profile.getWorkStreet());}

         return binding.getRoot();
    }

    @Override
    public void onSaveAndCloseClicked() {

        String street = binding.etStreetAddress.getText().toString();
        String cityStateZip = binding.etCityStateZip.getText().toString();
        String workStreet = binding.etWorkStreetAddress.getText().toString();
        String workCityStateZip = binding.etWorkCityStateZip.getText().toString();


        profile.setStreet(street);
        profile.setCityStateZip(cityStateZip);
        profile.setWorkStreet(workStreet);
        profile.setWorkCityStateZip(workCityStateZip);

        viewModel.updateUser(getViewModel(),profile);
    }

    @Override
    public void onEducationClicked() {

        ChangeFragments.addFragmentFadeIn(EditEducationFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EducationFragment",null);
    }

    @Override
    public void onProfileUpdated() {

        showToastShort("Profile Updated");

        for (Fragment fragment: getBaseActivity().getSupportFragmentManager().getFragments()){

            if (fragment instanceof InfoFragment){
                continue;

            } else {

                getBaseActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }
}
