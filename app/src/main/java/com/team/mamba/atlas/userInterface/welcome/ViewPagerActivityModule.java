package com.team.mamba.atlas.userInterface.welcome;


import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerViewModel;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.select_business_account.BusinessAccountsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewPagerActivityModule {

    @Provides
    ViewPagerViewModel providesViewPagerViewModel(){

        return new ViewPagerViewModel();
    }

    @Provides
    WelcomeViewModel providesWelcomeViewModel(){

        return new WelcomeViewModel();
    }

    @Provides
    BusinessAccountsViewModel providesBusinessAccountsViewModel(){

        return new BusinessAccountsViewModel();
    }
}
