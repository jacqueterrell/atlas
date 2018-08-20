package com.team.mamba.atlas.userInterface.welcome;


import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivityViewModel;
import com.team.mamba.atlas.userInterface.welcome._viewPager.ViewPagerViewModel;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
import com.team.mamba.atlas.userInterface.welcome.select_business_account.BusinessAccountsViewModel;

import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.enter_phone_number.EnterPhoneViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class WelcomeScreenModule {

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

    @Provides
    EnterPhoneViewModel providesEnterPhoneViewModel(){

        return new EnterPhoneViewModel();
    }

    @Provides
    WelcomeActivityViewModel providesWelcomeActivityViewModel(){

        return new WelcomeActivityViewModel();
    }
}