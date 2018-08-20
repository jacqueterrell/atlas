package com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.notes;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.YoYo.AnimatorCallback;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.model.api.PersonalNotes;
import com.team.mamba.atlas.data.model.api.UserProfile;
import com.team.mamba.atlas.databinding.ContactNotesBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.userInterface.dashBoard.profile.contacts_profile.ContactProfileFragment;
import javax.inject.Inject;

public class ContactNotesFragment extends BaseFragment<ContactNotesBinding,ContactNotesViewModel>
implements ContactNotesNavigator{


    @Inject
    ContactNotesViewModel viewModel;

    @Inject
    ContactNotesDataModel dataModel;

    private static UserProfile profile;
    private static int connectionType;
    private ContactNotesBinding binding;


    public static ContactNotesFragment newInstance(UserProfile userProfile){

        profile = userProfile;
        return new ContactNotesFragment();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.contact_notes;
    }

    @Override
    public ContactNotesViewModel getViewModel() {
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
        binding = getViewDataBinding();
        connectionType = ContactProfileFragment.getConnectionType();

        viewModel.requestUserNotes(getViewModel(),profile);

        return binding.getRoot();
    }

    @Override
    public void onUserNotesReturned() {

        binding.tvConnectionDate.setText(ContactProfileFragment.getConnectionDate());
        binding.tvConnectionType.setText(getConnectionTypeToString());
        binding.setPersonalNotes(viewModel.getPersonalNotes());

        String name = profile.getFirstName() + " " + profile.getLastName();
        binding.tvName.setText(name);
    }

    @Override
    public void onEditDetailsClicked() {

    }

    @Override
    public void onConnectionInfoClicked() {

        showConnectionInfoDialog();
    }

    @Override
    public void onNotesInfoClicked() {

        showNotesInfoDialog();
    }


    public String getConnectionTypeToString(){

        if (connectionType == 0){

            return "Family Member";

        } else if (connectionType == 1){

            return "Personal Friend";

        } else if (connectionType == 2){

            return "New Acquaintance";

        } else if (connectionType == 3){

            return "Business Contact";

        } else if (connectionType == 4){

            return "Colleague";

        } else {

            return "Client";
        }

    }

    private void showConnectionInfoDialog(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogConnectionInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogConnectionInfo);

    }

    private void showNotesInfoDialog(){

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart(animator -> binding.dialogPersonalNotesInfo.setVisibility(View.VISIBLE))
                .playOn(binding.dialogConnectionInfo);
    }

    @Override
    public void hideConnectionInfoDialog() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogConnectionInfo.setVisibility(View.GONE))
                .playOn(binding.dialogConnectionInfo);
    }

    @Override
    public void hidePersonalNotesInfoDialog() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onEnd(animator -> binding.dialogPersonalNotesInfo.setVisibility(View.GONE))
                .playOn(binding.dialogPersonalNotesInfo);
    }
}
