package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.team.mamba.atlas.BR;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.databinding.WelcomeScreenLayoutBinding;
import com.team.mamba.atlas.userInterface.base.BaseFragment;
import javax.inject.Inject;

public class WelcomeFragment extends BaseFragment<WelcomeScreenLayoutBinding,WelcomeViewModel> implements WelcomeNavigator{

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeDataModel dataModel;

    private WelcomeScreenLayoutBinding binding;

    public static WelcomeFragment newInstance(){

        return new WelcomeFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_screen_layout;
    }

    @Override
    public WelcomeViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View getProgressSpinner() {
        return null;
    }

    @Override
    public void onStartButtonClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onBusinessLoginClicked() {

        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .onStart(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.VISIBLE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onDateVerifyClicked() {

        validateAge();
       // onDateCancelClicked();
    }

    @Override
    public void onDateCancelClicked() {

        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .onEnd(animator -> binding.dialogVerifyAge.layoutVerifyAge.setVisibility(View.GONE))
                .playOn(binding.dialogVerifyAge.layoutVerifyAge);
    }

    @Override
    public void onFirstNameNextClicked() {

    }

    @Override
    public void onLastNameNextClicked() {

    }

    @Override
    public void onPhoneSubmitClicked() {

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

        Glide.with(getBaseActivity())
                .load(R.drawable.welcome_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(binding.imgViewBackground);

        return binding.getRoot();
    }

    private void validateAge(){

        int month =  (binding.dialogVerifyAge.datePicker.getMonth());
        int day = binding.dialogVerifyAge.datePicker.getDayOfMonth();
        int year = binding.dialogVerifyAge.datePicker.getYear();

        if (viewModel.isAgeValid(month,day,year)){

            showSnackbar("verified");

        } else {

            showSnackbar("not verified");
        }

    }

    private void showDatePicker(){

    }

    private void showFirstName(){

    }

    private void showLastName(){


    }
}
