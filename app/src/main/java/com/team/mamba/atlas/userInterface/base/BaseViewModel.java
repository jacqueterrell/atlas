package com.team.mamba.atlas.userInterface.base;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;
import com.team.mamba.atlas.data.AppDataManager;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private CompositeDisposable mCompositeDisposable;
    private N mNavigator;
    private AppDataManager dataManager;


    public BaseViewModel(){

        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();

    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }
}
