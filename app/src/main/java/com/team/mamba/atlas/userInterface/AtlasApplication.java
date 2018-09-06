package com.team.mamba.atlas.userInterface;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.dependencyInjection.component.DaggerApplicationComponent;
import com.team.mamba.atlas.utils.AppConstants;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

public class AtlasApplication extends Application implements HasActivityInjector,LifecycleObserver{

    public static boolean isAppInBackGround = false;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
//
//    @Inject
//    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);

        initializeLogger();
        setFirebaseSettings();

        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded(){

        isAppInBackGround = true;
        Logger.d("App in background");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded(){

        isAppInBackGround = false;
        Logger.d("App in foreground");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    private void initializeLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return AppConstants.APP_DEBUG;
            }
        });
    }

    private void setFirebaseSettings(){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
    }

//    @Override
//    public AndroidInjector<Service> serviceInjector() {
//        return dispatchingServiceInjector;
//    }
}
