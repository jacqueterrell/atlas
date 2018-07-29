package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerActivity;
import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ViewPagerActivityModule.class)
    abstract ViewPagerActivity bindViewPagerActivity();

}
