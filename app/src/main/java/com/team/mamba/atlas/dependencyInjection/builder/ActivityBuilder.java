package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.userInterface.dashBoard.DashBoardModule;
import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import com.team.mamba.atlas.userInterface.welcome._viewPager.ViewPagerFragment;
import com.team.mamba.atlas.userInterface.welcome.WelcomeScreenModule;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.BusinessAccountsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = WelcomeScreenModule.class)
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector(modules = DashBoardModule.class)
    abstract DashBoardActivity bindDashBoardActivity();

    @ContributesAndroidInjector(modules = WelcomeScreenModule.class)
    abstract BusinessAccountsActivity bindBusinessAccountsActivity();
}
