package com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users.recycler_view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.FindUsersRecyclerLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.add_contacts.find_users.FindUsersAdapter;
import com.team.mamba.atlas.userInterface.dashBoard.info.InfoFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class FindUsersRecycler extends BaseFragment<FindUsersRecyclerLayoutBinding,FindUsersRecyclerViewModel> implements
        FindUsersRecyclerNavigator {


    @Inject
    FindUsersRecyclerViewModel viewModel;

    @Inject
    FindUsersRecyclerDataModel dataModel;

    private FindUsersRecyclerLayoutBinding binding;
    private static List<UserProfile> queriedProfiles = new ArrayList<>();
    private FindUsersAdapter findUsersAdapter;

    public static FindUsersRecycler newInstance(List<UserProfile> profiles){

        queriedProfiles = profiles;
        return new FindUsersRecycler();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.find_users_recycler_layout;
    }

    @Override
    public FindUsersRecyclerViewModel getViewModel() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.find_users_recycler_layout,container,false);

        Collections.sort(queriedProfiles, (o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));

        findUsersAdapter = new FindUsersAdapter(queriedProfiles,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(findUsersAdapter);


        return binding.getRoot();
    }

    @Override
    public void onUsersRowClicked(UserProfile profile) {

        String last = profile.getLastName();
        String first = profile.getFirstName();

        String title = "Invite " + first + " " + last + " to connect?";
        String msg = "Please confirm that you know " + first + " " + last + " to send the invitation";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle(title)
                .setMessage(msg)
                .setNegativeButton("No",(paramDialogInterface, paramInt) ->{

                })
                .setPositiveButton("Yes", (paramDialogInterface, paramInt) -> {

                    //todo send the request to the db
                   // showInvitationSentAlert(first,last);
                    viewModel.initiateNewUserRequest(viewModel,profile);
                });

        dialog.show();
    }

    /**
     * Removes all Fragments off the stack except for the InfoFragment
     *
     * @param first the selected first name
     * @param last the selected last name
     */
    @Override
    public void showInvitationSentAlert(String first,String last){

        String title = "Invite sent to " + first + " " + last;

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle(title)
                .setPositiveButton("OK", (paramDialogInterface, paramInt) -> {

                    for (Fragment fragment: getActivity().getSupportFragmentManager().getFragments()){
                        if (fragment instanceof InfoFragment){
                            continue;
                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }

                });

        dialog.show();

    }

    @Override
    public void showAlreadyAContactAlert() {

        String title = getBaseActivity().getResources().getString(R.string.already_connected_title);
        String body = getBaseActivity().getResources().getString(R.string.already_connected_body);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseActivity());

        dialog.setTitle(title)
                .setCancelable(false)
                .setMessage(body)
                .setPositiveButton("Ok", (paramDialogInterface, paramInt) -> {

                    for (Fragment fragment: getBaseActivity().getSupportFragmentManager().getFragments()){
                        if (fragment instanceof InfoFragment){
                            continue;
                        } else {
                            getBaseActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }
                });

        dialog.show();
    }

    @Override
    public void handleError(String msg) {

    }
}
