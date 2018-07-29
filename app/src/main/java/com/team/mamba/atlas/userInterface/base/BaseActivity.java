package com.team.mamba.atlas.userInterface.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.team.mamba.atlas.BuildConfig;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.utils.NetworkUtils;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public abstract class BaseActivity <T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

//    @Inject
    AppDataManager appDataManager;

    private T mViewDataBinding;
    private V mViewModel;
    private String loadingTag = "BaseActivity";
    private static boolean isIdle = true;
    protected int sdk = Build.VERSION.SDK_INT;
    protected int marshMallow = Build.VERSION_CODES.M;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public abstract View getProgressSpinner();

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    protected boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showMessage(String message) {

        Snackbar.make(getViewDataBinding().getRoot(), message, Snackbar.LENGTH_LONG)
                .show();
    }

    protected void showAlertDialog(String title, String body) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(title)
                .setMessage(body)
                .setNegativeButton("Ok", (paramDialogInterface, paramInt) -> {

                });

        if (!isFinishing()) {

            dialog.show();

        }
    }

    protected void showNoDataAlert() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("No Network Connection")
                .setMessage("Please connect to a network")
                .setNegativeButton("Ok", (paramDialogInterface, paramInt) -> {

                });

        dialog.show();
    }

    protected void showTooLongAlert() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Whoops!")
                .setMessage("No internet connection found. Check your connection or try again")
                .setNegativeButton("Ok", (paramDialogInterface, paramInt) -> {

                });

        if (!isFinishing()) {

            dialog.show();

        }
    }

    protected void showProgressSpinner() {

        if (getProgressSpinner() != null) {

            getProgressSpinner().setVisibility(View.VISIBLE);
        }
    }

    protected void hideProgressSpinner() {

        if (getProgressSpinner() != null) {

            getProgressSpinner().setVisibility(View.GONE);
        }
    }


    protected void setIdlingResource(boolean idle, String tag) {

        if (BuildConfig.DEBUG){
            isIdle = idle;
            loadingTag = tag;
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
