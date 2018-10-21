package com.team.mamba.atlas.userInterface.dashBoard.announcements.create_announcement;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import com.team.mamba.atlas.databinding.CreateAnnouncementBinding;
import javax.inject.Inject;

public class CreateAnnouncementFragment extends BaseFragment<CreateAnnouncementBinding,CreateAnnouncementViewModel>
implements CreateAnnouncementNavigator{


    @Inject
    CreateAnnouncementViewModel viewModel;

    @Inject
    CreateAnnouncementDataModel dataModel;


    private CreateAnnouncementBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.create_announcement;
    }

    @Override
    public CreateAnnouncementViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }

    @Override
    public void onSendButtonClicked() {

        String msg = binding.etMessage.getText().toString();
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
         setUpSwitchListener();
         setUpEditText();

         return binding.getRoot();
    }

    private void setUpSwitchListener(){

        binding.switchFlipLabel.setOnCheckedChangeListener((compoundButton, isChecked) ->{

            if (isChecked){

                viewModel.setAnnouncment(true);
                binding.tvAnnouncement.setTextColor(getResources().getColor(R.color.black));
                binding.tvEvent.setTextColor(getResources().getColor(R.color.material_icons_light));
                binding.switchFlipLabel.getTrackDrawable().setColorFilter(getResources().getColor(R.color.dessert_green), PorterDuff.Mode.MULTIPLY);

            } else {

                viewModel.setAnnouncment(false);
                binding.tvAnnouncement.setTextColor(getResources().getColor(R.color.material_icons_light));
                binding.tvEvent.setTextColor(getResources().getColor(R.color.black));
                binding.switchFlipLabel.getTrackDrawable().setColorFilter(getResources().getColor(R.color.material_icons_light), PorterDuff.Mode.MULTIPLY);
            }
        });
    }

    private void setUpEditText(){

        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                int size = charSequence.length();
                int leftToGo = 140 - size;
                binding.tvCharRemaining.setText(String.valueOf(leftToGo));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
