package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.userInterface.dashBoard.DashBoardModule;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerActivity;
import com.team.mamba.atlas.userInterface.welcome.ViewPagerActivityModule;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.select_business_account.BusinessAccountsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ViewPagerActivityModule.class)
    abstract ViewPagerActivity bindViewPagerActivity();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract DashBoardActivity bindDashBoardActivity();

    @ContributesAndroidInjector(modules = ViewPagerActivityModule.class)
    abstract BusinessAccountsActivity bindBusinessAccountsActivity();
}
