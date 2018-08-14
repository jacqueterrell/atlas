package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.describe_connections;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.DescribeConnectionBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;

import javax.inject.Inject;

public class DescribeConnectionsFragment extends BaseFragment<DescribeConnectionBinding,DescribeConnectionsViewModel>
        implements DescribeConnectionsNavigator {

    @Inject
    DescribeConnectionsViewModel viewModel;

    @Inject
    DescribeConnectionsDataModel dataModel;

    private DescribeConnectionBinding binding;
    private static String lastName;
    private static String userCode;

    public static DescribeConnectionsFragment newInstance(String name,String code){

        lastName = name;
        userCode = code;
        return new DescribeConnectionsFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.describe_connection;
    }

    @Override
    public DescribeConnectionsViewModel getViewModel() {
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


         setUpListeners();
         return binding.getRoot();
    }

    @Override
    public void onFinishButtonClicked() {

       viewModel.addUserRequest(getViewModel(),lastName,userCode,getConnectionType());
    }

    @Override
    public void onInfoClicked() {

        showConnectionsInfo();
    }


    @Override
    public void hideConnectionsInfo() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogConnectionsInfo.setVisibility(View.GONE))
                .playOn(binding.dialogConnectionsInfo);
    }

    public void showConnectionsInfo(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogConnectionsInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogConnectionsInfo);
    }


    @Override
    public void onRequestSent() {

        String title = getBaseActivity().getResources().getString(R.string.connection_sent_title);
        String msg = getBaseActivity().getResources().getString(R.string.connection_sent_body);
        showAlert(title,msg);

//        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());
//
//        dialog.setTitle(title)
//                .setCancelable(false)
//                .setMessage(msg)
//                .setPositiveButton("Ok", (paramDialogInterface, paramInt) -> {
//
//                    getBaseActivity().getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                });
//
//        dialog.show();

    }

    @Override
    public void showUserNotFoundAlert() {

        String title = "User Not Found";
        String msg = "No user found with a last name of " + lastName + " and a code of " + userCode;
        showAlert(title,msg);
    }


    @Override
    public void showAlreadyAContactAlert() {

    }

    private void setUpListeners(){

        //family member
        binding.chkBoxFamilyMember.setOnClickListener(v -> {

            if (binding.chkBoxFamilyMember.isChecked()){

                binding.chkBoxPersonalFriend.setChecked(false);
                binding.chkBoxNewAcquaintance.setChecked(false);
                binding.chkBoxBusinessContact.setChecked(false);
                binding.chkBoxColleague.setChecked(false);
                binding.chkBoxClient.setChecked(false);

            } else {

                binding.chkBoxFamilyMember.setChecked(true);
            }
        });

        //personal friend
        binding.chkBoxPersonalFriend.setOnClickListener(v -> {

            if (binding.chkBoxPersonalFriend.isChecked()){

                binding.chkBoxFamilyMember.setChecked(false);
                binding.chkBoxNewAcquaintance.setChecked(false);
                binding.chkBoxBusinessContact.setChecked(false);
                binding.chkBoxColleague.setChecked(false);
                binding.chkBoxClient.setChecked(false);

            } else {

                binding.chkBoxPersonalFriend.setChecked(true);
            }
        });

        //new acquaintance
        binding.chkBoxNewAcquaintance.setOnClickListener(v -> {

            if (binding.chkBoxNewAcquaintance.isChecked()){

                binding.chkBoxPersonalFriend.setChecked(false);
                binding.chkBoxFamilyMember.setChecked(false);
                binding.chkBoxBusinessContact.setChecked(false);
                binding.chkBoxColleague.setChecked(false);
                binding.chkBoxClient.setChecked(false);

            } else {

                binding.chkBoxNewAcquaintance.setChecked(true);
            }
        });

        //Business contact
        binding.chkBoxBusinessContact.setOnClickListener(v -> {

            if (binding.chkBoxBusinessContact.isChecked()){

                binding.chkBoxPersonalFriend.setChecked(false);
                binding.chkBoxFamilyMember.setChecked(false);
                binding.chkBoxNewAcquaintance.setChecked(false);
                binding.chkBoxColleague.setChecked(false);
                binding.chkBoxClient.setChecked(false);

            } else {

                binding.chkBoxBusinessContact.setChecked(true);
            }
        });

        //Colleague
        binding.chkBoxColleague.setOnClickListener(v -> {

            if (binding.chkBoxColleague.isChecked()){

                binding.chkBoxPersonalFriend.setChecked(false);
                binding.chkBoxFamilyMember.setChecked(false);
                binding.chkBoxBusinessContact.setChecked(false);
                binding.chkBoxNewAcquaintance.setChecked(false);
                binding.chkBoxClient.setChecked(false);

            } else {

                binding.chkBoxColleague.setChecked(true);
            }
        });

        //Client
        binding.chkBoxClient.setOnClickListener(v -> {

            if (binding.chkBoxClient.isChecked()){

                binding.chkBoxPersonalFriend.setChecked(false);
                binding.chkBoxFamilyMember.setChecked(false);
                binding.chkBoxBusinessContact.setChecked(false);
                binding.chkBoxColleague.setChecked(false);
                binding.chkBoxNewAcquaintance.setChecked(false);

            } else {

                binding.chkBoxClient.setChecked(true);
            }
        });
    }


    private int getConnectionType(){

        if (binding.chkBoxFamilyMember.isChecked()){

            return 0;

        } else if (binding.chkBoxPersonalFriend.isChecked()){

            return 1;

        } else if (binding.chkBoxNewAcquaintance.isChecked()){

            return 2;

        } else if (binding.chkBoxBusinessContact.isChecked()){

            return 3;

        } else if (binding.chkBoxColleague.isChecked()){

            return 4;

        } else {

            return 5;
        }
    }
}
