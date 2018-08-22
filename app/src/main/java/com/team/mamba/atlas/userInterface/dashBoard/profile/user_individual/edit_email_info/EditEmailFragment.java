package com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_email_info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.databinding.EditEmailLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivityNavigator;
import com.team.mamba.atlas.userInterface.dashBoard.profile.user_individual.edit_address_info.EditAddressFragment;
import com.team.mamba.atlas.utils.ChangeFragments;
import javax.inject.Inject;

public class EditEmailFragment extends BaseFragment<EditEmailLayoutBinding,EditEmailViewModel> implements EditEmailNavigator {


    @Inject
    EditEmailDataModel dataModel;

    @Inject
    EditEmailViewModel viewModel;

    private EditEmailLayoutBinding binding;
    private static UserProfile profile;
    private DashBoardActivityNavigator parentNavigator;

    public static EditEmailFragment newInstance(UserProfile userProfile){

        profile = userProfile;
        return new EditEmailFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_email_layout;
    }

    @Override
    public EditEmailViewModel getViewModel() {
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

         setCachedData();

         return binding.getRoot();
    }

    @Override
    public void onContinueClicked() {

        setEmailData();

        ChangeFragments.addFragmentFadeIn(EditAddressFragment.newInstance(profile),getBaseActivity()
                .getSupportFragmentManager(),"EmailFragment",null);
    }

    @Override
    public void onSaveAndCloseClicked() {

        showProgressSpinner();
        setEmailData();
        viewModel.updateUser(getViewModel(),profile);
    }

    private void setEmailData(){

        String personalEmail = binding.etPersonalEmail.getText().toString();
        String workEmail = binding.etWorkEmail.getText().toString();
        Long timeStamp = System.currentTimeMillis() / 1000;


        profile.setTimestamp(timeStamp);
        profile.setEmail(personalEmail);
        profile.setWorkEmail(workEmail);
    }

    @Override
    public void onProfileUpdated() {

        hideProgressSpinner();
        parentNavigator.resetToFirstFragment();
        showToastShort("Profile Updated");
    }

    private void setCachedData(){

        binding.etPersonalEmail.setText(profile.getEmail());
        binding.etWorkEmail.setText(profile.getWorkEmail());
    }
}
