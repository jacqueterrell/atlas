package com.team.mamba.atlas.userInterface.welcome;


import com.team.mamba.atlas.userInterface.welcome._viewPagerActivity.ViewPagerViewModel;
import com.team.mamba.atlas.userInterface.welcome.welcomeScreen.WelcomeViewModel;
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
}
